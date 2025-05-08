package webProject.model.repository.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // 페이징 처리 메서드
    Page<RequestEntity> findByMemberEntity(MemberEntity memberEntity, Pageable pageable);
    Page<RequestEntity> findByReqrole(int reqrole, Pageable pageable);

    // 검색 기능을 위한 메서드 (선택 사항)
    Page<RequestEntity> findByReqroleAndReqtitleContaining(int reqrole, String keyword, Pageable pageable);

    // N+1 문제 해결을 위한 페치 조인 쿼리
    @Query("SELECT r FROM RequestEntity r LEFT JOIN FETCH r.estimateEntities WHERE r.reqrole = :reqrole")
    List<RequestEntity> findByReqroleWithEstimates(@Param("reqrole") int reqrole);

    // 페이징과 함께 사용하기 위한 카운트 쿼리
    @Query(value = "SELECT r FROM RequestEntity r WHERE r.reqrole = :reqrole",
            countQuery = "SELECT COUNT(r) FROM RequestEntity r WHERE r.reqrole = :reqrole")
    Page<RequestEntity> findByReqroleWithPaging(@Param("reqrole") int reqrole, Pageable pageable);
}
