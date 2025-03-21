package webProject.service.request;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;
import webProject.model.repository.estimate.EstimateRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.request.RequestRepository;
import webProject.service.estimate.EstimateService;
import webProject.service.kakao.KakaoAddressService;
import webProject.service.member.MemberService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private EstimateRepository estimateRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EstimateService estimateService;

    // 현재 로그인된 회원의 요청글 전체조회
    public List<RequestDto> requestFindAll() {
        // 1. 로그인 된 유저 정보 가져오기
        String loginid = memberService.getSession();
        MemberEntity memberEntity = memberRepository.findByMemail(loginid);

        // 2.회원의 role에 따라 조회리스트 필터링
        List<RequestEntity> requestEntityList;
        if (memberEntity.getRole().equals("requester")) {
            // 로그인유저가 의뢰인일 경우 본인이 작성한 요청글만 조회
            requestEntityList = requestRepository.findByMemberEntity(memberEntity);
        } else {
            // 로그인유저가 업체,마스터이면 해당하는 reqrole을 가진 글만 조회
            int reqrole = memberEntity.getRole().equals("master") ? 2 : 1;
            requestEntityList = requestRepository.findByReqrole(reqrole);
        }// if else end

        // 3. 조회된 회원의 요청글 엔티티를 dto로 변환
        List<RequestDto> requestDtoList = new ArrayList<>();
        requestEntityList.forEach(entity -> {
            // 탈퇴한 회원의 글이 포함되어있으면 NulLpointExcettion 예외에 대한 처리 로직 추가
            try {
                RequestDto requestDto = entity.toDto();
                // 각 요청글의 견적서 수를 계산하여 DTO에 설정
                requestDto.setEstimateCount(estimateRepository.countByRequestEntity_Reqno(entity.getReqno()));
                requestDtoList.add(requestDto);
            } catch (NullPointerException e) {
                System.out.println("회원의 요청글 전체조회에서 예외처리");
                RequestDto requestDto = RequestDto.builder()
                        .reqno(entity.getReqno())
                        .mno(0)
                        .mname("탈퇴한 회원입니다.")
                        .reqtitle(entity.getReqtitle())
                        .reqcontent(entity.getReqcontent())
                        .reqspace(entity.getReqspace())
                        .latitude(entity.getLatitude())
                        .longitude(entity.getLongitude())
                        .reqstate(entity.isReqstate())
                        .reqrole(entity.getReqrole())
                        .build();
                requestDto.setEstimateCount(estimateRepository.countByRequestEntity_Reqno(entity.getReqno()));
                requestDtoList.add(requestDto);
            }
        });
        return requestDtoList;
    }

    // 현재 로그인된 회윈의 요청글 개별조회
    public RequestDto requestFind(int reqno) {

        // 1. 조회할 요청글의 매개변수 reqno, 요청글의 엔티티를 조회
        Optional<RequestEntity> optional = requestRepository.findById(reqno);

        // 2. 조회할 엔티티의 여부를 받아오기
        if (optional.isPresent()) {
            RequestEntity requestEntity = optional.get();

            // 마감기한이 지났는지 여부 확인 (채택은 estimate에서 처리됨)
            if (requestEntity.isReqstate() && requestEntity.isDeadlineReached()) {
                // reqstate가 true(활성화)이고 deaeline이 true(7일이 지났는지)인지 확인
                // 즉, 요청글이 아직 활성화 상태인데 deaeline이 7일이 지난 글을 찾는 조건문
                requestEntity.setReqstate(false); //마감 처리
                requestRepository.save(requestEntity);
            }// if end

            // 엔티티가 있으면 반환
            return requestEntity.toDto();
        }// if end

        return null;
    }// requestFind end

    @Autowired
    private KakaoAddressService kakaoAddressService;

    // 견적 요청글 작성
    @Transactional
    public boolean requestPost(RequestDto requestDto) {
        // 1. 사용자로부터 전달받은 requestDto를 엔터티로 변환
        RequestEntity requestEntity = requestDto.toEntity();

        // + 카카오 주소 API를 통해 좌표 가져오기
        String address = requestDto.getRaddress(); // 주소 필드 받아오가
        double[] coordinates = kakaoAddressService.getCoordinatesFromAddress(address);

        if (coordinates != null) {
            // 3. 좌표를 기존 RequestEntity에 업데이트
            requestEntity.setLatitude(coordinates[0]);  // 위도
            requestEntity.setLongitude(coordinates[1]);  // 경도
            requestEntity.setRaddress(address);  // 주소 저장
        }
        // -2. 견적서 작성자는 현재 로그인된 회원이므로 세션에서 현재 로그인된 회원번호를 조회
        MemberDto loginDto = memberService.getMyInfo();
        // 로그인된 상태가 아니면 글쓰기 종료
        if (loginDto == null) return false;

        // 로그인된 상태이면 회원번호를 조회
        int loginMno = loginDto.getMno();
        // 로그인된 회원 엔티티를 요청서 엔티티에 대입한다.
        MemberEntity loginEntity = memberRepository.findById(loginMno).get();
        requestEntity.setMemberEntity(loginEntity);

        // 2. 엔티티 저장
        RequestEntity saveRequestEntity = requestRepository.save(requestEntity);

        // 게시글 등록 여부에 따라 true/false 반환
        if (saveRequestEntity.getReqno() > 0) {
            return true;
        } else {
            return false;
        } // if-else end
    } // requestPost end

    // ========== 위치에 따른 거리계산 및 출력 ==========
    private double userLatitude;
    private double userLongitude;

    // 사용자 위치 설정, (post)로 받은 위치 정보
    public boolean setUserLocation(double latitude, double longtitude) {
        this.userLatitude = latitude;
        this.userLongitude = longtitude;
        return true;
    }
    // 거리 계산 된 요청서 리스트 반환 메서드(GET 요청시 호출)
    public List<RequestDto> getNearRequests() {
        // 로그인 된 유저 정보 가져오기
        String loginid = memberService.getSession();  // 세션에서 로그인된 유저의 이메일을 가져옴
        MemberEntity memberEntity = memberRepository.findByMemail(loginid);  // 이메일을 통해 회원 정보 조회
        // 예외 처리 1: 회원 정보가 null일 경우 처리
        if (memberEntity == null) {
            // 회원 정보가 없으면 예외를 발생시킴
            throw new IllegalArgumentException("회원 정보가 제공되지 않았습니다.");
        }
        // 모든 요청서를 조회
        List<RequestEntity> requestEntityList = requestRepository.findAll();
        // 예외 처리 2: 회원의 역할에 따른 필터링
        if (memberEntity.getRole().equals("requester")) {
            // 로그인유저가 'requester'(의뢰인)일 경우 본인이 작성한 요청글만 조회
            requestEntityList = requestRepository.findByMemberEntity(memberEntity);
        } else if (memberEntity.getRole().equals("master") || memberEntity.getRole().equals("company")) {
            // 로그인유저가 'master' 또는 'company'일 경우 해당하는 reqrole을 가진 글만 조회
            int reqrole = memberEntity.getRole().equals("master") ? 2 : 1;  // 'master'는 2, 'company'는 1
            requestEntityList = requestRepository.findByReqrole(reqrole);
        } else {
            // 잘못된 역할일 경우 예외 처리
            throw new IllegalArgumentException("잘못된 역할 정보입니다.");
        }
        // 예외 처리 3: 요청서 목록이 없을 경우 처리
        if (requestEntityList.isEmpty()) {
            // 요청서가 없으면 예외를 발생시킴
            throw new RuntimeException("조회할 요청서가 없습니다.");
        }
        System.out.println("requestEntityList => " + requestEntityList);  // 디버깅 용도: 요청서 리스트 출력
        List<RequestDto> nearbyRequestList = new ArrayList<>();  // 반환할 요청서 DTO 리스트
        // 각 요청서에 대해 거리를 계산하고 DTO 객체에 추가
        requestEntityList.forEach(requestEntity -> {
            // 현재 사용자와 요청서 간의 거리를 계산
            double distance = calculateDistance(userLatitude, userLongitude, requestEntity.getLatitude(), requestEntity.getLongitude());

            // 요청서를 DTO로 변환하고 거리 정보 추가
            RequestDto requestDto = requestEntity.toDto();
            requestDto.setDistance(distance);  // 거리 추가
            nearbyRequestList.add(requestDto);  // 리스트에 추가
        });
        // 요청서 리스트를 거리 순으로 정렬
        nearbyRequestList.sort(Comparator.comparingDouble(RequestDto::getDistance));

        // 정렬된 요청서 리스트 반환
        return nearbyRequestList;
    }

    // 두 지점 간의 거리 계산 메서드 (Haversine 공식을 사용)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;  // 지구 반지름 (단위: km)

        // 위도, 경도 차이를 라디안으로 변환
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        System.out.println(lat2);  // 디버깅: 경도 출력
        System.out.println(lat1);  // 디버깅: 위도 출력
        System.out.println(lon2);  // 디버깅: 경도 출력
        System.out.println(lon1);  // 디버깅: 위도 출력

        // Haversine 공식
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 실제 거리 계산 (단위: km)
        return R * c;
    }
}

