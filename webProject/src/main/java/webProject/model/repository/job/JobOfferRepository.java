package webProject.model.repository.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.entity.job.JobOfferEntity;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Integer> {

    List<JobOfferEntity> findByMemberEntity_Mno(int mno);


    // 회원탈퇴시 JobOfferENtity의 mno를 Null로 업데이트하는 기능
    @Modifying
    @Query("UPDATE JobOfferEntity j SET j.memberEntity = null WHERE j.memberEntity.mno = :mno")
    void unlinkMember(Integer mno);
}
