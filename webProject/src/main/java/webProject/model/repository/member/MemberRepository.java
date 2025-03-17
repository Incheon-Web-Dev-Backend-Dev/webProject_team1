package webProject.model.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webProject.model.entity.member.MemberEntity;
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Integer> {
    boolean existsByMemailAndMpwd( String memail , String mpwd );

    //이메일로 엔티티조회
    MemberEntity findByMemail( String memail );

    // 이메일 중복 확인 쿼리
    boolean existsByMemail(String memail);
}
