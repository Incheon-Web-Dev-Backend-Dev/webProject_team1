package webProject.service.estimate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webProject.model.dto.estimate.EstimateDto;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.like.LikeEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;
import webProject.model.entity.review.ReviewEntity;
import webProject.model.entity.review.ReviewFileEntity;
import webProject.model.repository.estimate.EstimateRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.request.RequestRepository;
import webProject.model.repository.review.ReviewFileRepository;
import webProject.model.repository.review.ReviewRepository;
import webProject.service.mail.MailService;
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
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ReviewFileRepository reviewFileRepository;
    @Autowired private MailService mailService;

    // 견적글 쓰기
    public boolean estimateWrite(EstimateDto estimateDto) {
        EstimateEntity estimateEntity = estimateDto.toESEntity();
        // 현재 로그인된 세션 객체 조회
        MemberDto loginDto = memberService.getMyInfo();
        if (loginDto == null) {
            System.out.println("login error");
            return false;
        }
        // 로그인된 상태이면 회원번호 조회
        int loginMno = loginDto.getMno();

        // 1. 로그인된 회원번호를 글쓰기 작성자로 대입
        MemberEntity loginEntity = memberRepository.findById(loginMno).get();
        estimateEntity.setMemberEntity(loginEntity);
        // 2.
        RequestEntity requestEntity = requestRepository.findById(estimateDto.getReqno()).get();
        estimateEntity.setRequestEntity(requestEntity);

        EstimateEntity saveEntity = estimateRepository.save(estimateEntity);
        if (saveEntity.getEstno() > 0) {
            // 메일 전송 (예외 발생해도 실패 처리하지 않음)
            try {
                mailService.sendEstimateNotificationToRequester(saveEntity);
            } catch (Exception e) {
                System.out.println("메일 전송 중 오류 발생: " + e.getMessage());
            }
            return true;
        }
        return false;
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
                try {
                    EstimateDto estimateDto = entity.toESDto();
                    estimateDtoList.add(estimateDto);
                }catch (NullPointerException e){
                    System.out.println(e);
                    EstimateDto estimateDto = EstimateDto.builder()
                            .estno(entity.getEstno())
                            .mno(0)
                            .mname("탈퇴한 회원입니다.")
                            .esttitle(entity.getEsttitle())
                            .estcontent(entity.getEstcontent())
                            .estcash(entity.getEstcash())
                            .eststate(entity.isEststate())
                            .reqstate(entity.getRequestEntity().isReqstate())
                            .build();
                    estimateDtoList.add(estimateDto);
                }
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

    // 견적글 채택 여부 함수
    @Transactional
    public boolean selectEstimate(int estno) {

        // 1. 견적서 엔티티 조회
        Optional<EstimateEntity> optionalEstimate = estimateRepository.findById(estno);

        // 2. 견적서개 존재하면
        if(optionalEstimate.isPresent()) {
            EstimateEntity estEntity = optionalEstimate.get();
            RequestEntity reqEntity  = estEntity.getRequestEntity();

            // 이미 마감된 요청글인지 여부 확인
            if(reqEntity.isDeadlineReached() || !reqEntity.isReqstate()) {
                return false;// 마감되었거나 채택 된 글이면 false반환
            } // if end

            // 견적서 채택 및 요청글 마감처리
            estEntity.setEststate(true); // 견적글 채택됨
            reqEntity.setReqstate(false); // 요청글 마감됨
            return true;
        } // if end
        return false;
    }// selectEstimate end
    
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

    // 견적서 삭제하기
    @Transactional
    public boolean estimateDelete(int estno) {
        // 로그인된 사용자의 정보 가져오기
        MemberDto loginDto = memberService.getMyInfo();
        if (loginDto == null) {
            System.out.println("login error");
            return false;
        }
        try {
            List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
            List<ReviewFileEntity> reviewFileEntityList = reviewFileRepository.findAll();
            reviewEntityList.forEach(entity -> {
                if (entity.getEstimateEntity().getEstno() == estno) {
                    reviewRepository.delete(entity);
                }
            });
            reviewFileEntityList.forEach(entity -> {
                if (entity.getReviewEntity().getEstimateEntity().getEstno() == estno) {
                    reviewFileRepository.delete(entity);
                }
            });
            // 견적서 삭제
            estimateRepository.deleteById(estno);
            return true;
        } catch (Exception e) {
            // 예외 발생 시 스택 트레이스를 출력하고 false 반환
            e.printStackTrace();
            return false;
        }
    }
}
