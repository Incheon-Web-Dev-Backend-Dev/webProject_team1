package webProject.model.repository.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.job.JobFileEntity;

@Repository
public interface JobFileRepository extends JpaRepository<JobFileEntity, Integer> {
}
