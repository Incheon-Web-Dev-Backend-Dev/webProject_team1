package webProject.model.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.like.LikeEntity;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
}
