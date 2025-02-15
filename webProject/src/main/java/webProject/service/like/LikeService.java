package webProject.service.like;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.like.LikeDto;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.job.JobOfferEntity;
import webProject.model.entity.like.LikeEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.repository.job.JobOfferRepository;
import webProject.model.repository.like.LikeRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.service.member.MemberService;

@Service
public class LikeService {
    @Autowired LikeRepository likeRepository;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired JobOfferRepository jobOfferRepository;

    @Transactional
    public boolean likePost(LikeDto likeDto){
        MemberDto loginDto = memberService.getMyInfo();
        JobOfferEntity jobOfferEntity = jobOfferRepository.findById(likeDto.getJono()).get();
        MemberEntity memberEntity = memberRepository.findById(loginDto.getMno()).get();
        likeDto.setLstate(true); // 좋아요 누르면 기본값으로 true

        LikeEntity likeEntity = likeDto.toLEntity();
        likeEntity.setMemberEntity(memberEntity);
        likeEntity.setJobOfferEntity(jobOfferEntity);
        LikeEntity saveEntity = likeRepository.save(likeEntity);

        if (saveEntity.getLno() > 0) {return true;}
        return false;
    }



    public int likeFind(int mno, int jono) {
        // 구인글 번호, 회원 번호가 매칭되는 like_job entity 가 존재하는지
        LikeEntity likeEntity = likeRepository.findLike(mno, jono);

        if ( likeEntity == null ) {return 0;                    // entity 가 존재하지 않을 때 = 처음 지원할 때
        } else if ( likeEntity.isLstate() == false ) {return 1; // 지원했다가 취소했을 때
        } else {return 2;}                                      // 지원한 상태일 때
    }

    @Transactional
    public boolean likeUpdate(int jono) {
        MemberDto loginDto = memberService.getMyInfo();
        MemberEntity memberEntity = memberRepository.findById(loginDto.getMno()).get();
        LikeEntity likeEntity = likeRepository.findLike(memberEntity.getMno(), jono);
        likeEntity.setLstate(!likeEntity.isLstate());
        return true;
    }
}
