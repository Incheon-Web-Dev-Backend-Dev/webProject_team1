package webProject.model.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webProject.model.entity.like.LikeEntity;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    @Query( value = "delete from like_job where jono = :jono;" , nativeQuery = true)
    boolean deleteByQuery(@Param("jono") int jono);

    // 로그인한 계정번호 와 현재 보고 있는 구인글번호가 같은 컬럼 찾기
    @Query( value = "select * from like_job where mno = :mno and jono = :jono;", nativeQuery = true )
    LikeEntity findLike(int mno, int jono);

<<<<<<< HEAD
    @Query( value = "select * from like_job where lstate = true and jono = :jono;", nativeQuery = true )
    List<LikeEntity> findLikeMaster(int jono);
=======
    // 회원탈퇴할 때 LikeENtity에 mno값이 Null 로 업데이트 되게 처리
    @Modifying
    @Query("UPDATE LikeEntity l SET l.memberEntity = null WHERE l.memberEntity.mno = :mno")
    void unlinkMember(Integer mno);
>>>>>>> 9de46a957f09dd0b0d8e717d55ecba7a09b9d9b4
}
