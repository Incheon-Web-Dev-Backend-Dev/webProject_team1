package webProject.model.repository.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.entity.job.JobOfferEntity;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Integer> {

    List<JobOfferEntity> findByMemberEntity_Mno(int mno);
}
