package webProject.model.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
    // 1. 특정 회원 엔티티의 요청글 목록 조회
    List<RequestEntity> findByMemberEntity(MemberEntity memberEntity);
    // 2. 역할에 따른 요청글 목록 조회
    List<RequestEntity> findByReqrole(int reqrole);

    // 회원탈퇴시 RequestEntity의 mno를 Null로 업데이트하는 기능
    @Modifying
    @Query("UPDATE RequestEntity r SET r.memberEntity = null WHERE r.memberEntity.mno = :mno")
    void unlinkMember(Integer mno);
}
