package webProject.service.request;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;
import webProject.model.repository.estimate.EstimateRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.request.RequestRepository;
import webProject.service.estimate.EstimateService;
import webProject.service.member.MemberService;

import javax.management.relation.Role;
import java.util.*;

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

        // 2. 모든 요청서의 엔티티 조회
        List<RequestEntity> requestEntityList = requestRepository.findByMemberEntity(memberEntity);

        // 3. 조회된 회원의 요청글 엔티티를 dto로 변환
        List<RequestDto> requestDtoList = new ArrayList<>();
        requestEntityList.forEach( entity -> {
            RequestDto requestDto = entity.toDto();

            // 각 요청글의 견적서 수를 계산하여 DTO에 설정
            requestDto.setEstimateCount(estimateRepository.countByRequestEntity_Reqno(entity.getReqno()));
            requestDtoList.add( requestDto );
        });

        return requestDtoList;
    }


    // 현재 로그인된 회윈의 요청글 개별조회
    public RequestDto requestFind(int reqno) {

       // 1. 조회할 요청글의 매개변수 reqno, 요청글의 엔티티를 조회
        Optional< RequestEntity> optional = requestRepository.findById(reqno);

        // 2. 조회할 엔티티의 여부를 받아오기
        if(optional.isPresent()) {
            RequestEntity requestEntity = optional.get();
            RequestDto requestDto = requestEntity.toDto();

            // 엔티티가 있으면 반환
            return requestDto;
        }// if end

        return null;
    }// requestFind end

    // 견적 요청글 작성
    @Transactional
    public boolean requestPost (RequestDto requestDto){
        // 1. 사용자로부터 전달받은 requestDto를 엔테테로 변환
        // -1. dto를 entity로 변환
        RequestEntity requestEntity = requestDto.toEntity();
        // -2. 견적서 작성자는 현재 로그인된 회원이므로 세션에서 현재 로그인된 회원번호를 조회
        MemberDto loginDto = memberService.getMyInfo();

        // 로그인된 상태가 아니면 글쓰기 종료
        if(loginDto == null) return false;

        // 로그인된 상태이면 회원번호를 조회
        int loginMno = loginDto.getMno();
        // 로그인된 회원 엔티티를 요청서 엔티티에 대입한다.
        MemberEntity loginEntity = memberRepository.findById( loginMno ).get();
        requestEntity.setMemberEntity( loginEntity );

        // 2. 엔티티 저장
        RequestEntity saveRequestEntity = requestRepository.save( requestEntity );

        // 게시글 등록 여부에 따라 true/false 반환
        if(saveRequestEntity.getReqno() > 0 ) {
            return true;
        } else {
            return false;
        } // if-else end
    } // requestPost end

    // *역할에 따른 요청서 나눠 보기* 0215 추가(임준수) + roll 이 아니구 사실 role 이라는 진실
//    public List<RequestDto> requestFindRoll(String role) {
//
//        MemberDto loginDto = memberService.getMyInfo();
//        if(loginDto == null){System.out.println("login error"); return null;}
//        int a;
//        if(role.equals("company")){ a = 1;}else { a= 2;}
//        // 변수안에 있는 String 값 비교 시 변수명.equals(비교값)
//        List<RequestEntity> requestEntityList = requestRepository.findAll();
//
//        List<RequestDto> requestDtoList = new ArrayList<>();
//        requestEntityList.forEach(entity -> {
//            if (entity.getReqrole() == a){
//                RequestDto requestDto = entity.toDto();
//                requestDtoList.add(requestDto);
//            }
//        });
//        return requestDtoList;
//    }


}
