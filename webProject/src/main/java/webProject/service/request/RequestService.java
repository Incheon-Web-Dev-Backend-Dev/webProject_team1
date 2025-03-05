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

}


