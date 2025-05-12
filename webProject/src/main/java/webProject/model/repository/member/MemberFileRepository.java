package webProject.model.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.member.MemberFileEntity;

import java.util.List;

@Repository
public interface MemberFileRepository extends JpaRepository<MemberFileEntity, Integer> {
    // 회원탈퇴하면 memberfile 엔티티에서 해당 컬럼 삭제
    void deleteByMemberEntity_Mno(Integer mno);

    /**
     * 회원의 프로필 이미지를 조회합니다.
     * @param memberEntity 회원 엔티티
     * @return 프로필 이미지 엔티티
     */
    MemberFileEntity findByMemberEntityAndProfilenameIsNotNull(MemberEntity memberEntity);

    /**
     * 회원의 첨부 파일 목록을 조회합니다. (프로필 이미지 제외)
     * @param memberEntity 회원 엔티티
     * @return 첨부 파일 엔티티 목록
     */
    List<MemberFileEntity> findByMemberEntityAndMfnameIsNotNull(MemberEntity memberEntity);
}
