package webProject.controller.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.like.LikeDto;
import webProject.model.dto.member.MemberDto;
import webProject.service.like.LikeService;

import java.util.List;

@RestController
public class LikeController {
    @Autowired LikeService likeService;

    // 첫 지원
    @PostMapping("/like/post.do")
    public boolean likePost(@RequestBody LikeDto likeDto){
        return likeService.likePost(likeDto);
    }

    // 본인 지원여부 판별
    @GetMapping("/like/find.do")
    public int likeFind(@RequestParam int mno, @RequestParam int jono){ return likeService.likeFind(mno, jono);}

    // 지원상태 수정
    @PutMapping("/like/update.do")
    public boolean likeUpdate(@RequestParam int jono) {return likeService.likeUpdate(jono);}

    // 지원자 리스트 출력
    @GetMapping("/like/list.do")
    public List<MemberDto> likeList(@RequestParam int jono){return likeService.likeList(jono);}
}
