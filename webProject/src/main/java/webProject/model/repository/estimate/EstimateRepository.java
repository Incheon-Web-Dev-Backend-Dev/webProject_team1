package webProject.model.repository.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;

import java.util.List;

@Repository
public interface EstimateRepository extends JpaRepository<EstimateEntity, Integer> {

    // reqno에 해당하는 요청글의 갯수를 확인하는 메서드
    long countByRequestEntity_Reqno(int reqno);

    List<EstimateEntity> findByMemberEntity(MemberEntity memberEntity);


    // member 회원탈퇴시 estmate에 참조된 Mno null값으로 업데이트하는 기능
    @Modifying
    @Query("UPDATE EstimateEntity e SET e.memberEntity = null WHERE e.memberEntity.mno = :mno")
    void unlinkMember(Integer mno);

    // estimste 견적서삭제시 참조되어있는 Reqno null 값으로 업데이트하는 기능추가
}
