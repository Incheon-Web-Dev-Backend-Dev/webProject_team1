package webProject.model.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.member.MemberFileEntity;

@Repository
public interface MemberFileRepository extends JpaRepository<MemberFileEntity, Integer> {
}
