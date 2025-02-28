package webProject.model.repository.job;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webProject.model.entity.job.JobOfferEntity;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Integer> {

    List<JobOfferEntity> findByMemberEntity_Mno(int mno);


    // 회원탈퇴시 JobOfferENtity의 mno를 Null로 업데이트하는 기능
    @Modifying
    @Query("UPDATE JobOfferEntity j SET j.memberEntity = null WHERE j.memberEntity.mno = :mno")
    void unlinkMember(Integer mno);


    // 페이징 및 검색 ( service / 지역 설정해서 검색 가능하게 하고 싶음 )
//    @Query(value = "select * from joboffer where" +
//            " if(:joservice = '' and :jocity = '' and :jodistrict = '' and :keyword = '', true,  " +
//            " if(joservice = 'joservice', joservice like %:joservice%," +
//            " if(jocity = 'jocity', jocity like %:jocity%," +
//            " if(jodistrict = 'jodistrict', jodistrict like %:jodistrict%," +
//            " if(:key = 'jotitle', jotitle like %:keyword%," +
//            " if(:key = 'jocontent', jocontent like %:keyword%, true)))", nativeQuery = true)
    @Query(value = "SELECT * FROM joboffer " +
            "WHERE if (:keyword='',true," +
            "    (:joservice IS NULL OR joservice LIKE %:joservice%) AND" +
            "    (:jocity IS NULL OR jocity LIKE %:jocity%) AND" +
            "    (:jodistrict IS NULL OR jodistrict LIKE %:jodistrict%) AND" +
            "    (:key IS NULL OR :key = 'jotitle' AND jotitle LIKE %:keyword% " +
            "    OR :key = 'jocontent' AND jocontent LIKE %:keyword% " +
            "    OR :key = ''))",
            nativeQuery = true)
    Page<JobOfferEntity> findBySearch(String joservice, String jocity, String jodistrict, String key, String keyword, Pageable pageable);
// if 조건이 실행 되었을 때 가정
// SQL IF 조건문 : if(조건,참,거짓)
// SQL IF 조건문 중첩 : if(조건1, 참1, if(조건2, 참2, if(조건3, 참3 , 그외거짓)))

// 1. 전체조회 : select * from board where cno = :cno and true
// 2. 제목조회 : select * from board where cno = :cno and btitle like %:keyWord%
// 3. 내용조회 : select * from board where cno = :cno and bcontent like %:keyWord%
// 4. 그외 : select * from board where cno = :cno and true}
}