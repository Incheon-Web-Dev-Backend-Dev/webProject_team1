package webProject.model.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webProject.model.entity.like.LikeEntity;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    @Query( value = "delete from like_job where jono = :jono;" , nativeQuery = true)
    boolean deleteByQuery(@Param("jono") int jono);



}
