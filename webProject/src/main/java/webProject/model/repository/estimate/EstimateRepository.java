package webProject.model.repository.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.estimate.EstimateEntity;

@Repository
public interface EstimateRepository extends JpaRepository<EstimateEntity, Integer> {
}
