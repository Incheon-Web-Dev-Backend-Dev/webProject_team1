package webProject.model.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import webProject.model.entity.review.ReviewFileEntity;

public interface ReviewFileRepository extends JpaRepository<ReviewFileEntity, Integer> {
}
