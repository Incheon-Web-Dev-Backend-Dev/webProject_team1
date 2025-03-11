package webProject.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.review.ReviewDto;
import webProject.service.review.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // 1. 리뷰 작성하기
    // REST API 형식에 맞게 컨트롤러에서는 ResponseEntity<?> 로 반환타입을 변경한다ㅣ.
    // HTTP 샅태에 따라 필요한 메시지로 반환해줘야 함
    // @ModelAttribute 는 폼데이터(multipart/form-data) 형식을 처리한다. 리뷰 업로드에 파일이 입력되므로 @RequestBody대신 사용
    @PostMapping("/write.do")
    public ResponseEntity<?> reviewWrite(@ModelAttribute ReviewDto reviewDto){
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
    }// reviewWrite end


    // 2. 리뷰 상세조회
    @GetMapping("/view.do")
    public ResponseEntity<?> reviewView(@RequestParam int revno) {
        try{
            ReviewDto result = reviewService.reviewView(revno);
            if(result.getRevno() > 0) { // 조회한 review 글의 정보가 나오면
                // 성공 : 201 created 상태코드에 대한 반환 내용 반환
                return ResponseEntity.status(HttpStatus.CREATED).body("review.view status OK" + result);
            } else {
                // 서비스 로직은 성공했지만 리뷰 등록은 실패했을 때 반환 내용
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("review.view status BAD");
            }// if- else end
        } catch(Exception e) {
            // 서버 내부 오류 : 500 상태코드에 대한 반환 내용
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("review.view status error" + e.getMessage());
        }// try-catch end
    }// reviewView end
}
