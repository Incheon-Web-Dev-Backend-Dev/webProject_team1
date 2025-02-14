package webProject.model.repository.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.estimate.EstimateEntity;

@Repository
public interface EstimateRepository extends JpaRepository<EstimateEntity, Integer> {

    // reqno에 해당하는 요청글의 갯수를 확인하는 메서드
    long countByRequestEntity_Reqno(int reqno);
}
