package webProject.controller.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.like.LikeDto;
import webProject.model.dto.member.MemberDto;
import webProject.service.like.LikeService;

import java.util.List;

@RestController
public class LikeController {

    @Autowired
    LikeService likeService;

    // 첫 지원 - 구인글에 대한 '좋아요' 또는 지원을 처리
    @PostMapping("/like/post.do")
    public ResponseEntity<String> likePost(@RequestBody LikeDto likeDto) {
        boolean isLiked = likeService.likePost(likeDto);
        if (isLiked) {
            return new ResponseEntity<>("Successfully liked the job offer.", HttpStatus.CREATED);  // 성공 시 201 Created
        } else {
            return new ResponseEntity<>("Failed to like the job offer.", HttpStatus.INTERNAL_SERVER_ERROR);  // 실패 시 500 Internal Server Error
        }
    }

    // 본인 지원 여부 판별 - 사용자가 특정 구인글에 대해 '좋아요'를 했는지 확인
    @GetMapping("/like/find.do")
    public ResponseEntity<Integer> likeFind(@RequestParam int mno, @RequestParam int jono) {
        int likeStatus = likeService.likeFind(mno, jono);
        if (likeStatus == 0) {
            return new ResponseEntity<>(likeStatus, HttpStatus.NOT_FOUND);  // 지원하지 않으면 404 Not Found
        }
        return new ResponseEntity<>(likeStatus, HttpStatus.OK);  // 지원한 경우 200 OK
    }

    // 지원 상태 수정 - 특정 구인글에 대한 '좋아요' 상태를 변경
    @PutMapping("/like/update.do")
    public ResponseEntity<String> likeUpdate(@RequestParam int jono) {
        boolean isUpdated = likeService.likeUpdate(jono);
        if (isUpdated) {
            return new ResponseEntity<>("Successfully updated like status.", HttpStatus.OK);  // 상태 업데이트 성공 시 200 OK
        } else {
            return new ResponseEntity<>("Failed to update like status.", HttpStatus.INTERNAL_SERVER_ERROR);  // 상태 업데이트 실패 시 500 Internal Server Error
        }
    }

    // 지원자 리스트 출력 - 특정 구인글에 지원한 회원들의 목록을 반환
    @GetMapping("/like/list.do")
    public ResponseEntity<List<MemberDto>> likeList(@RequestParam int jono) {
        List<MemberDto> memberList = likeService.likeList(jono);
        if (memberList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 지원자가 없으면 204 No Content
        }
        return new ResponseEntity<>(memberList, HttpStatus.OK);  // 지원자가 있으면 200 OK
    }
}
