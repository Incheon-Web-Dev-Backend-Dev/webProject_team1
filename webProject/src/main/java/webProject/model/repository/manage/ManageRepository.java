package webProject.model.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.member.MemberEntity;

@Repository
public interface ManageRepository extends JpaRepository<MemberEntity, Integer> {
}
