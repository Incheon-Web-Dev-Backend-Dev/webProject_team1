package webProject.model.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.member.MemberFileEntity;

@Repository
public interface MemberFileRepository extends JpaRepository<MemberFileEntity, Integer> {
    // 회원탈퇴하면 memberfile 엔티티에서 해당 컬럼 삭제
    void deleteByMemberEntity_Mno(Integer mno);
}
