package webProject.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.review.ReviewDto;
import webProject.service.review.ReviewService;

import java.util.List;

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
                // 성공 : 200 상태코드에 대한 반환 내용 반환
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                // 요청형식 에러 : 400 서비스 로직은 성공했지만 리뷰 조회는 실패했을 때 반환 내용
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("review.view status BAD");
            }// if- else end
        } catch(Exception e) {
            // 서버 내부 오류 : 500 상태코드에 대한 반환 내용
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("review.view status error" + e.getMessage());
        }// try-catch end
    }// reviewView end

    // 3. 리뷰 전체조회
    @GetMapping("/viewall.do")
    public ResponseEntity<?> reviewViewAll(){
        try{
            List<ReviewDto> result = reviewService.reviewViewAll();
            if(result == null) { // review 리스트가 없고
                // 로그인 정보도 없으면
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("review.viewAll : please login");
            } else if (result.isEmpty()) { // 로그인은 했지만 review 리스트가 비어있으면
                return ResponseEntity.status(HttpStatus.OK).body("review.viewAll status OK : review list null");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }// if-else end
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("review.viewAll status error" + e.getMessage());
        }// try-catch end
    }// review viewAll end

    // 4. index 페이지에서 리뷰 2건만 조회(최신글 중 별점 높고 revimgfile이 존재하는 글로)
    @GetMapping("/mainReview.do")
    public ResponseEntity<?> mainTopReview() {
        try{
            List<ReviewDto> topReviews = reviewService.mainTopReview();
            return ResponseEntity.status(HttpStatus.OK).body(topReviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( "mainTopReview error"+ e.getMessage());
        } // try-catch end
    }// mainTopReview end

    // 5. 리뷰 수정
    @PutMapping("/modify.do")
    public ResponseEntity<?> modifyReview(@ModelAttribute ReviewDto modifyRev){
        try {
            boolean result = reviewService.modifyReview(modifyRev);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("modify review error" + e.getMessage());
        }// try - catch end
    }// modifyReview end

    // 6. 리뷰 삭제
    @DeleteMapping("/delete.do")
    public ResponseEntity<?> deleteReview(@RequestParam int delRevno){
        try {
            boolean result = reviewService.deleteReview(delRevno);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("modify review error" + e.getMessage());
        }// try - catch end
    }
}
