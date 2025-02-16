package webProject.model.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
    // 1. 특정 회원 엔티티의 요청글 목록 조회
    List<RequestEntity> findByMemberEntity(MemberEntity memberEntity);

    List<RequestEntity> findByMemberEntity_Role(String role);

    // 2. 역할에 따른 요청글 목록 조회
    List<RequestEntity> findByReqrole(int reqrole);
}
