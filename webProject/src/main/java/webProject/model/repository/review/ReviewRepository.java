package webProject.model.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webProject.model.entity.review.ReviewEntity;
import webProject.model.entity.review.ReviewFileEntity;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {


    // 견적서 삭제 메서드가 실행되면 reventity의 estno를 Null 으로 바꿔주는 기능
    @Modifying
    @Query("UPDATE ReviewEntity r SET r.estimateEntity = null WHERE r.estimateEntity  IS NOT NULL AND r.estimateEntity.estno = :estno")
    void unlinkEstimate(Integer estno);

    @Modifying
    @Query("UPDATE ReviewEntity r SET r.memberEntity = null WHERE r.memberEntity.mno = :mno")
    void unlinkMember(Integer mno);


}

