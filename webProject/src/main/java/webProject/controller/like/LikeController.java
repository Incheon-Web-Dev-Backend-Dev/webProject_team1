package webProject.controller.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import webProject.model.dto.like.LikeDto;
import webProject.service.like.LikeService;

@RestController
public class LikeController {
    @Autowired LikeService likeService;
//
//    @PostMapping("/like/post.do")
//    public boolean likePost(@RequestBody LikeDto likeDto){
//        return likeService.likePost(likeDto);
//    }

    @DeleteMapping("/like/delete.do")
    public boolean likeDelete(){return false;}
}
