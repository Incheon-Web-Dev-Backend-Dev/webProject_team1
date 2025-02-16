package webProject.model.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webProject.model.entity.review.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    //@Transactional
    //@Modifying
    //@Query("UPDATE ReviewEntity r SET r.estimateEntity = null WHERE r.estimateEntity IS NOT NULL AND r.estimateEntity.estno = :estno")
    //void unlinkEstimate(Integer estno);


}

