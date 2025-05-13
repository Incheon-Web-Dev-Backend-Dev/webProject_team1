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

    @Query(value = "select * from joboffer", nativeQuery = true)
    Page<JobOfferEntity> findByAll(Pageable pageable);

    // 페이징 및 검색 ( service / 지역 설정해서 검색 가능하게 하고 싶음 )
//    @Query(value = "select * from joboffer where" +
//            " if(:joservice = '' and :jocity = '' and :jodistrict = '' and :keyword = '', true,  " +
//            " if(joservice = 'joservice', joservice like %:joservice%," +
//            " if(jocity = 'jocity', jocity like %:jocity%," +
//            " if(jodistrict = 'jodistrict', jodistrict like %:jodistrict%," +
//            " if(:key = 'jotitle', jotitle like %:keyword%," +
//            " if(:key = 'jocontent', jocontent like %:keyword%, true)))", nativeQuery = true)
    @Query(value = "select * from joboffer where 1=1 and " +
            "if(:keyword = '', true, " +
            "if(:key = 'jotitle', jotitle like %:keyword%, " +
            "if(:key = 'jocontent', jocontent like %:keyword%, true)))",
            nativeQuery = true)
    Page<JobOfferEntity> findBySearch(String key, String keyword, Pageable pageable);


    @Query(value = "select * from joboffer where mno = :mno and " +
            " if(:keyword = '', true," +
            " if(:key = 'jotitle', jotitle like %:keyword%," +
            " if(:key = 'jocontent', jocontent like %:keyword%, true)))",
            nativeQuery = true)
    Page<JobOfferEntity> findByMy(int mno, String key, String keyword, Pageable pageable);
// if 조건이 실행 되었을 때 가정
// SQL IF 조건문 : if(조건,참,거짓)
// SQL IF 조건문 중첩 : if(조건1, 참1, if(조건2, 참2, if(조건3, 참3 , 그외거짓)))

// 1. 전체조회 : select * from board where cno = :cno and true
// 2. 제목조회 : select * from board where cno = :cno and btitle like %:keyWord%
// 3. 내용조회 : select * from board where cno = :cno and bcontent like %:keyWord%
// 4. 그외 : select * from board where cno = :cno and true}
    // 무한 스크롤 생성
    @Query(value = "select * from joboffer where 1=1 and " +
            "if(:keyword = '', true, " +
            "if(:key = 'jotitle', jotitle like %:keyword%, " +
            "if(:key = 'jocontent', jocontent like %:keyword%, true))) " +
            "AND jono < :lastId " +
            "ORDER BY jono DESC LIMIT :limit",
            nativeQuery = true)
    List<JobOfferEntity> findForInfiniteScroll(String key, String keyword, int lastId, int limit);

    @Query(value = "select * from joboffer where mno = :mno and " +
            "if(:keyword = '', true, " +
            "if(:key = 'jotitle', jotitle like %:keyword%, " +
            "if(:key = 'jocontent', jocontent like %:keyword%, true))) " +
            "AND jono < :lastId " +
            "ORDER BY jono DESC LIMIT :limit",
            nativeQuery = true)
    List<JobOfferEntity> findMyJobsForInfiniteScroll(int mno, String key, String keyword, int lastId, int limit);
}