package webProject.controller.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.like.LikeDto;
import webProject.service.like.LikeService;

@RestController
public class LikeController {
    @Autowired LikeService likeService;

    @PostMapping("/like/post.do")
    public boolean likePost(@RequestBody LikeDto likeDto){
        return likeService.likePost(likeDto);
    }

    @GetMapping("/like/find.do")
    public int likeFind(@RequestParam int mno, @RequestParam int jono){ return likeService.likeFind(mno, jono);}

    @PutMapping("/like/update.do")
    public boolean likeUpdate(@RequestParam int jono) {return likeService.likeUpdate(jono);}
}
