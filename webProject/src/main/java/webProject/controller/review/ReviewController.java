package webProject.controller.review;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.review.ReviewDto;
import webProject.service.review.ReviewService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // 리뷰 작성하기
    // REST API 형식에 맞게 컨트롤러에서는 ResponseEntity<?> 로 반환타입을 변경한다ㅣ.
    // HTTP 샅태에 따라 필요한 메시지로 반환해줘야 함
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
    }

    // QR code 받기
    @GetMapping("/qr")
    public ResponseEntity<byte[]> qrToReview() throws WriterException, IOException {

        // QR 정보
        int width = 200;
        int height = 200;
        String url = "http://localhost:8080/review/write";

        // QR Code - BitMatrix : qr code 정보 생성
        BitMatrix encode = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);

        // QR Code 이미지 생성 (1회성)
        // stream으로 Generate(1회성이 아니면 file로 작성?)
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(encode, "PNG", out);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(out.toByteArray());
        }catch (Exception  e) {

            System.out.println("review qr error" + e.getMessage());
        }
        return null;
    }
}
