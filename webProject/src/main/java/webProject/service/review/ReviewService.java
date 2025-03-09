package webProject.service.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.review.ReviewDto;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.review.ReviewEntity;
import webProject.model.entity.review.ReviewFileEntity;
import webProject.model.repository.estimate.EstimateRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.review.ReviewFileRepository;
import webProject.model.repository.review.ReviewRepository;
import webProject.service.member.MemberService;

import java.util.Optional;

@Service
public class ReviewService {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EstimateRepository estimateRepository;
    @Autowired private ReviewFileRepository reviewFileRepository;

    @Autowired private MemberService memberService;
    @Autowired private ReviewFileService reviewFileService;

    // 1. 리뷰 작성하기
    @Transactional
    public boolean reviewWrite(ReviewDto reviewDto){
        ReviewEntity reviewEntity = reviewDto.toEntity();
        // 현재 로그인된 세션 객체 조회
        MemberDto loginDto = memberService.getMyInfo();
        Optional<MemberEntity> optionalMember = memberRepository.findById(loginDto.getMno());
        if(!optionalMember.isPresent()) {
            System.out.println("login error");
            return false;
        }
        // 1. 엔티티 꺼내기
        MemberEntity memberEntity = optionalMember.get();
        reviewEntity.setMemberEntity(memberEntity);

        // 2. 작성될 리뷰의 견적서 번호를 가져옴
        EstimateEntity estimateEntity = estimateRepository.findById(reviewDto.getEstno()).get();
        reviewEntity.setEstimateEntity(estimateEntity);

        // 3. 리뷰 엔티티 저장
        ReviewEntity saveReviewEntity = reviewRepository.save(reviewEntity);

        // 4. 파일 처리
        if(saveReviewEntity.getRevno() > 0 ){// 리뷰 작성을 성공했으면
            if(reviewDto.getUploadReviewFiles() != null && !reviewDto.getUploadReviewFiles().isEmpty()) {

                // 1. 하나씩 업로드 서비스에 업로드 요청
                reviewDto.getUploadReviewFiles().forEach((file) -> {
                    // 2. 하나씩 업로드 된 파일명 반환받기
                    String fileName = reviewFileService.reviewFileUpload(file);
                    if( fileName != null) {
                        // 3. 하나씩 업로드된 파일명으로 리뷰파일 엔티티 생성
                        ReviewFileEntity reviewFileEntity = ReviewFileEntity.builder()
                                .reviewEntity( saveReviewEntity)
                                .revfname(fileName)
                                .build();
                        reviewFileRepository.save( reviewFileEntity );
                        System.out.println("review file upload service . fileName" + fileName);
                    }
                });// foreach end
            }
            return true;
        } else {
            return false;
        }// if-else end

    }// reviewWrite end

    public ReviewDto reviewView(int revno){
        // 1. 조회할 리뷰 revno, 리뷰글의 엔티티를 조회
        Optional<ReviewEntity> optional = reviewRepository.findById(revno);

        // 2. 조회할 엔티티의 여부 받아오기
        if(optional.isPresent()) {
            ReviewEntity reviewEntity = optional.get();
            reviewRepository.save(reviewEntity);
            // 엔티티가 있으면 반환
            return reviewEntity.toDto();
        }// if end
        //없으면 null 반환
        return null;
    }// review view end

    // 2. 리뷰 상세조회


    // estimate가 삭제될 때 매핑 관계를 끊는 메서드
    @Transactional
    public void unlinkEstimateFromReview(Integer estno) {
        // EstimateEntity와 연결된 ReviewEntity에서 관계를 끊음
        reviewRepository.unlinkEstimate(estno);
    }


}
