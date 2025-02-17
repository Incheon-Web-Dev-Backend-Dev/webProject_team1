package webProject.service.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webProject.model.repository.review.ReviewRepository;

@Service
public class ReviewService {
    @Autowired private ReviewRepository reviewRepository;
    @Transactional
    public void unlinkEstimateFromReview(Integer estno) {
        // EstimateEntity와 연결된 ReviewEntity에서 관계를 끊음
        reviewRepository.unlinkEstimate(estno);
    }
}
