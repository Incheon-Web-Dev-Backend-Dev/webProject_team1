package webProject.model.repository.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.job.LikeEntity;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
}
