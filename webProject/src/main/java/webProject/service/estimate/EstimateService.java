package webProject.service.estimate;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.estimate.EstimateDto;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;
import webProject.model.repository.estimate.EstimateRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.request.RequestRepository;
import webProject.service.member.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstimateService {
    @Autowired private EstimateRepository estimateRepository;
    @Autowired private MemberService memberService;
    @Autowired private RequestEntity requestEntity;
    @Autowired private MemberRepository memberRepository;
    @Autowired private RequestRepository requestRepository;

    // 견적글 쓰기
    public boolean estimateWrite(EstimateDto estimateDto){
        EstimateEntity estimateEntity = estimateDto.toESEntity();
        // 현재 로그인된 세션 객체 조회
        MemberDto loginDto = memberService.getMyInfo();
        if(loginDto == null){ System.out.println("login error"); return false; }
        // 로그인된 상태이면 회원번호 조회
        int loginMno = loginDto.getMno();

        // 1. 로그인된 회원번호를 글쓰기 작성자로 대입
        MemberEntity loginEntity = memberRepository.findById(loginMno).get();
        estimateEntity.setMemberEntity(loginEntity);
        // 2.
        RequestEntity requestEntity = requestRepository.findById(estimateDto.getReqno()).get();
        estimateEntity.setRequestEntity(requestEntity);

        EstimateEntity saveEntity = estimateRepository.save(estimateEntity);
        if(saveEntity.getEstno()>0){
            return true;
        }else {
            return false;
        }
    }

    // 견적글 전체 보기
    public List<EstimateDto> estimateFindAll(int reqno){
        // 현재 로그인된 세션 객체 조회
        MemberDto loginDto = memberService.getMyInfo();
        if(loginDto == null){ System.out.println("login error"); return null; }
        // 모든 견적글 엔티티 조회
        List<EstimateEntity> estimateEntityList = estimateRepository.findAll();
        // 모든 견적 저장할 DTO 선언
        List<EstimateDto> estimateDtoList =new ArrayList<>();
        estimateEntityList.forEach(entity -> {
            if(entity.getRequestEntity().getReqno() == reqno ) {
                EstimateDto estimateDto = entity.toESDto();
                estimateDtoList.add(estimateDto);
            }
        });
        return estimateDtoList;
    }
    @Transactional
    // 견적글 개별 조회
    public EstimateDto estimateFind(int estno){
        // 현재 로그인된 세션 객체 조회
        MemberDto loginDto = memberService.getMyInfo();
        if(loginDto == null){ System.out.println("login error"); return null; }
        // 조회할 특정 견적글 번호 엔티티를 조회
        Optional<EstimateEntity> optional = estimateRepository.findById(estno);
        // 만약 조회된 엔티티가 있다면 꺼내서 -> DTO 로
        if (optional.isPresent()){
            EstimateEntity estimateEntity = optional.get();
            EstimateDto estimateDto = estimateEntity.toESDto();
            return estimateDto;
        } {
            System.out.println("값 없음");
        }return null;
    }
    // 현재 로그인된 회원의 작성항 견적글 천제 조회
    public List<EstimateDto> estimateMyWrote(){
        // 로그인된 회원 아이디 가져오기
        String loginid = memberService.getSession();
        MemberEntity memberEntity = memberRepository.findByMemail(loginid);
        List<EstimateEntity> estimateEntityList = estimateRepository.findByMemberEntity(memberEntity);
        List<EstimateDto> estimateDtoList = new ArrayList<>();
        estimateEntityList.forEach(entity -> {
            EstimateDto estimateDto = entity.toESDto();
            estimateDtoList.add(estimateDto);
        });
        return estimateDtoList;
    }

    // 견적글 삭제하기
    public boolean estimateDelete (int estno) {
        // 현재 로그인된 세션 객체 조회
        MemberDto loginDto = memberService.getMyInfo();
        if(loginDto == null){ System.out.println("login error"); return false;}
        estimateRepository.deleteById(estno);
        return false;
    }
}
