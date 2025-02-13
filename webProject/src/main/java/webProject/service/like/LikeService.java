package webProject.service.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.like.LikeDto;
import webProject.model.dto.member.MemberDto;
import webProject.model.repository.like.LikeRepository;
import webProject.service.member.MemberService;

@Service
public class LikeService {
    @Autowired LikeRepository likeRepository;
    @Autowired MemberService memberService;
    public boolean likePost(LikeDto likeDto){
        return false;
    }

//    public boolean likeDelete(int jono) {
//        String mid = memberService.getSession();
//        if(mid != null){
//            likeRepository.deleteById();
//            return true;
//        }
//        System.out.println("login error, login please");
//        return false;
//    }

}
