package webProject.model.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import webProject.model.entity.review.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
}
