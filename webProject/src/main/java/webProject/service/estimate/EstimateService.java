package webProject.service.estimate;

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
        List<EstimateEntity> estimateEntityList = estimateRepository.findAll();
        List<EstimateDto> estimateDtoList =new ArrayList<>();
        estimateEntityList.forEach(entity -> {
            if(entity.getRequestEntity().getReqno() == reqno ) {
                EstimateDto estimateDto = entity.toESDto();
                estimateDtoList.add(estimateDto);
            } else {

            }
        });
        return estimateDtoList;
    }
    // 견적글 개별 조회
    public EstimateDto estimateFind(int eno){
        return null;
    }
    // 현재 로그인된 회원이 작성한 견적글 개별 조회
    public List<EstimateDto> estimateMyFind(int eno){
        return null;
    }
}
