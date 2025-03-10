package webProject.model.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import webProject.model.entity.review.ReviewFileEntity;

import java.util.List;

public interface ReviewFileRepository extends JpaRepository<ReviewFileEntity, Integer> {
    // 특정 리뷰 파일을 조회
    List<ReviewFileEntity> findByReviewEntity_Revno(int revno);


}
