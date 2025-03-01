package webProject.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webProject.model.dto.review.ReviewDto;
import webProject.service.review.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // 리뷰 작성하기
    // REST API 형식에 맞게 컨트롤러에서는 ResponseEntity<?> 로 반환타입을 변경한다ㅣ.
    // HTTP 샅태에 따라 필요한 메시지로 반환해줘야 함
    @PostMapping("/write.do")
    public ResponseEntity<?> reviewWrite(@RequestBody ReviewDto reviewDto){
        try{
            boolean result = reviewService.reviewWrite(reviewDto);
            if(result) {
                // 성공 : 201 created 상태코드에 대한 반환 내용
                return ResponseEntity.status(HttpStatus.CREATED).body("review.write status OK");
            } else {
                // 서비스 로직은 성공했지만 리뷰 등록은 실패했을 때 반환 내용
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("review.write status BAD");
            }// if- else end
        } catch(Exception e) {
            // 서버 내부 오류 : 500 상태코드에 대한 반환 내용
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("review.write status error" + e.getMessage());
        }// try-catch end
    }
}
