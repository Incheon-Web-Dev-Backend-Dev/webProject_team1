package webProject.model.repository.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.job.JobOfferEntity;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Integer> {
}
