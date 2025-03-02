package webProject.service.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.review.ReviewDto;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.review.ReviewEntity;
import webProject.model.repository.estimate.EstimateRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.review.ReviewRepository;
import webProject.service.member.MemberService;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EstimateRepository estimateRepository;

    @Autowired private MemberService memberService;
    @Autowired private ReviewFileService reviewFileService;

    // 1. 리뷰 작성하기
    @Transactional
    public boolean reviewWrite(ReviewDto reviewDto){

        // 리뷰 사진 첨부파일 존재하면 업로드 진행
        if(reviewDto.getUploadReviewFile() == null || reviewDto.getUploadReviewFile().isEmpty()) { // 업로드 파일이 없으면
            reviewDto.setRevimg(null); // null 대입
        } else { // 리뷰 파일 존재하면 업로드 처리
            List<String> reviewFileNames = reviewFileService.reviewFileUpload(reviewDto.getUploadReviewFile());
            reviewDto.setRevimg(reviewFileNames);
        }

        ReviewEntity reviewEntity = reviewDto.toEntity();
        // 1. 로그인 세션객체 조회
        MemberDto loginDto = memberService.getMyInfo();
        if(loginDto == null) {
            System.out.println("login error");
            return false;
        }
        int loginMno = loginDto.getMno();

        // 2. 회원 존재 여부 확인
        Optional<MemberEntity> optionalMember = memberRepository.findById(loginMno);
        if(!optionalMember.isPresent()) {
            System.out.println("회원을 찾을 수 없습니다: " + loginMno);
            return false;
        }
        MemberEntity loginEntity = optionalMember.get();
        reviewEntity.setMemberEntity(loginEntity);

        // 3. 견적서 존재 여부 확인
        Optional<EstimateEntity> optionalEstimate = estimateRepository.findById(reviewDto.getEstno());
        if(!optionalEstimate.isPresent()) {
            System.out.println("견적서를 찾을 수 없습니다: " + reviewDto.getEstno());
            return false;
        }

        // 4. 리뷰 객체에 견적서 설정
        EstimateEntity estimateEntity = optionalEstimate.get();
        reviewEntity.setEstimateEntity(estimateEntity);

        ReviewEntity saveEntity = reviewRepository.save(reviewEntity);
        if(saveEntity.getRevno() > 0 ) {
            return true;
        } else {
            return false;
        } // if-else end
    }


    @Transactional
    public void unlinkEstimateFromReview(Integer estno) {
        // EstimateEntity와 연결된 ReviewEntity에서 관계를 끊음
        reviewRepository.unlinkEstimate(estno);
    }


}
