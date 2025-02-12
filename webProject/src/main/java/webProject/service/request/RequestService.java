package webProject.service.request;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.request.RequestRepository;
import webProject.service.estimate.EstimateService;
import webProject.service.member.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
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
            requestDtoList.add( requestDto );
        });

        return requestDtoList;
    }

    // 요청글에 응답이 온 견적서 수를 계산해주는 메서드
    public int countEstimate () {

        return 0;
    }

    // 현재 로그인된 회윈의 요청글 개별조회
    public RequestDto requestFind(int reqno) {

        // 1. 조회할 특정 요청글의 번호 매개변수 reqno의 엔티티를 조회한다.
        Optional< RequestEntity > optional = requestRepository.findById( reqno );
        // 조회된 엔티티 여부에 따라 true/false 반환
        if(optional.isPresent() ) {
            RequestEntity requestEntity = optional.get(); // optional 엔티티 꺼내고
            RequestDto requestDto = requestEntity.toDto(); // 엔티티를 Dto 변환

            // 게시글에 댓글 조회한것처럼 요청글에 견적서 들어오면 견적서 제목, 가격, 내용 보여주기
            //->

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
}
