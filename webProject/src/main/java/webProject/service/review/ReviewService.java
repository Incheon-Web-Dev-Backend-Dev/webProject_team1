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

import java.util.ArrayList;
import java.util.List;
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

    // 2. 리뷰 상세조회
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

    // 3. 리뷰 전체조회(로그인한 모든 회원이 조회 가능)
    public List<ReviewDto> reviewViewAll() {

        // 2. 로그인 유저 없으면 null 반환, 있으면 목록 출력
        List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        reviewEntityList.forEach(entity -> {
            ReviewDto reviewDto = entity.toDto();
            reviewDtoList.add(reviewDto);
        });// foreach end

        // 3. 목록 반환
        return reviewDtoList;
    }


    // 4. index에서 보여주는 review
    public List<ReviewDto> mainTopReview() {
        // 1. 이미지가 있고, 별점이 높은 최신 리뷰 2개 조회
        List<ReviewEntity> topReviewEntities = reviewRepository.findTopReviewsWithImages();


        // 2. DTO로 변환
        List<ReviewDto> topReviewDtos = new ArrayList<>();
        topReviewEntities.forEach(entity -> {
            ReviewDto dto = entity.toDto();
            topReviewDtos.add(dto);
        });//foreach end

        return topReviewDtos;
    }

    // 5. 리뷰 수정
    @Transactional
    public boolean modifyReview(ReviewDto modifyRevDto){
        // 1. 로그인한 회원의 정보를 가져오기
        int loginMno = memberService.getMyInfo().getMno();
        System.out.println("loginMno" + loginMno);
        System.out.println("getMno" + modifyRevDto.getMno());
        // 2. 로그인한 회원의 정보가 리뷰 작성자와 같으면 리뷰 수정 허용
        if(loginMno == modifyRevDto.getMno()){
            int getRevno = modifyRevDto.getRevno();
            ReviewEntity reviewEntity = reviewRepository.findByRevno(getRevno);
            reviewEntity.setRevcontent(modifyRevDto.getRevcontent());
            reviewEntity.setRevstar(modifyRevDto.getRevstar());
            reviewEntity.setReviewFileEntityList(modifyRevDto.toEntity().getReviewFileEntityList());
            return true;
        } else{
            return false;
        }// if-else end
    }

    // 6. 리뷰 삭제
    @Transactional
    public boolean deleteReview(int delRevno){

        // 1. 로그인 세션 객체 조회
        String loginMid = memberService.getSession();
        if(loginMid == null) {
            System.out.println("login error");
            return false;
        } else {
            // 2. 리뷰에 업로드된 이미지 파일이 있으면 해당 엔티티를 찾아서 먼저 삭ㅈ=
            List<ReviewFileEntity> reviewFileEntityList = reviewFileRepository.findAll();
            reviewFileEntityList.forEach( entity -> {
                if(entity.getReviewEntity().getRevno() == delRevno) {
                    reviewFileRepository.delete(entity);
                } // if end
            }); // foreach end

            // 3. 리뷰 삭제
            reviewRepository.deleteById(delRevno);

            return true;
        } // if-else end
    }// deleteReview end


    // estimate가 삭제될 때 매핑 관계를 끊는 메서드
    @Transactional
    public void unlinkEstimateFromReview(Integer estno) {
        // EstimateEntity와 연결된 ReviewEntity에서 관계를 끊음
        reviewRepository.unlinkEstimate(estno);
    }


}
