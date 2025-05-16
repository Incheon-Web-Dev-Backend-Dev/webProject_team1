package webProject.controller.estimate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.estimate.EstimateDto;
import webProject.service.estimate.EstimateService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/estimate")
public class EstimateController {
    @Autowired EstimateService estimateService;
    // REST API 형식에 맞게 컨트롤러에서는 ResponseEntity<?> 로 반환타입을 변경한다ㅣ.
    // HTTP 샅태에 따라 필요한 메시지로 반환해줘야 함
    // body에 text -> "" 있을 경우 JS Fetch에서 r.text 로 처리
    // 견적글 작성하기
    @PostMapping("/write.do") // HTTP POST 방식으로 "/write.do" 경로에 요청이 들어오면 이 메소드가 실행됩니다.
    public ResponseEntity<?> estimateWrite(@RequestBody EstimateDto estimateDto) { // 요청 본문(body)에 담긴 EstimateDto 객체를 파라미터로 받습니다.
        try {
            // estimateService의 estimateWrite 메소드를 호출하여 견적서 업로드 작업을 수행합니다.
            boolean result = estimateService.estimateWrite(estimateDto);

            if(result) { // 만약 결과가 true라면, 즉 업로드가 성공했으면
                // 성공한 경우: 201 Created 상태코드를 반환하며 성공 메시지와 함께 응답을 보냅니다.
                HashMap<String, Object> response = new HashMap<>();
                response.put("success", true); // 성공 여부를 true로 설정
                response.put("message", "견적서 업로드 성공"); // 성공 메시지
                return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 상태코드와 응답 본문을 반환
            } else { // 서비스 로직은 성공했지만 견적서 업로드 자체가 실패한 경우
                // 실패한 경우: 400 Bad Request 상태코드를 반환하며 실패 메시지와 함께 응답을 보냅니다.
                HashMap<String, Object> response = new HashMap<>();
                response.put("success", false); // 성공 여부를 false로 설정
                response.put("message", "견적서 업로드 실패"); // 실패 메시지
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 상태코드와 응답 본문을 반환
            }
        } catch(Exception e) { // 예외가 발생한 경우 (서버 내부 오류 등)
            // 서버 오류가 발생한 경우: 500 Internal Server Error 상태코드를 반환하며 오류 메시지를 포함하여 응답을 보냅니다.
            HashMap<String, Object> response = new HashMap<>();
            response.put("success", false); // 성공 여부를 false로 설정
            response.put("message", "서버 오류: " + e.getMessage()); // 예외 메시지 포함
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 상태코드와 응답 본문을 반환
        }
    }
    // 요청번호(reqno)에 대한 모든 견적서 조회
    @GetMapping("/findall.do")
    public ResponseEntity<?> estimateFindAll(@RequestParam int reqno) {
        try {
            List<EstimateDto> result = estimateService.estimateFindAll(reqno);

            // 조회한 견적서가 있으면
            if (result != null && !result.isEmpty()) {
                // 성공: 200 상태코드와 함께 견적서 목록을 반환
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                // 요청형식 에러: 400 상태코드로 빈 리스트 반환
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No estimates found for this request.");
            }
        } catch (Exception e) {
            // 서버 내부 오류: 500 상태코드와 오류 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving estimates: " + e.getMessage());
        }
    }
    // 견적글 상세조회
    @GetMapping("/find.do")
    public ResponseEntity<?> estimateFind(@RequestParam int estno) {
        try {
            EstimateDto result = estimateService.estimateFind(estno);
            if(result.getEstno() > 0) { // 조회한 견적글의 정보가 존재하면
                // 성공 : 200 상태코드에 대한 반환 내용 반환
                return ResponseEntity.status(HttpStatus.OK).body(result);  // 수정된 부분
            } else {
                // 요청형식 에러 : 400 서비스 로직은 성공했지만 견적 조회는 실패했을 때 반환 내용
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("estimate.view status BAD");
            }
        } catch (Exception e) {
            // 서버 내부 오류 : 500 상태코드에 대한 반환 내용
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("estimate.view status error" + e.getMessage());
        }
    }
    // 견적글 채택여부
    @PostMapping("/select.do")
    public ResponseEntity<?> selectEstimate(@RequestParam int estno) {
        try{
            boolean result = estimateService.selectEstimate(estno);

            if(result == true){
                return ResponseEntity.status(HttpStatus.OK).body("Select Success");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed Select");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("status error" + e.getMessage());
        }
    }
    // 로그인한 회원이 작성한 견적글 리스트
    @GetMapping("/mywrote.do")
    public ResponseEntity<List<EstimateDto>> estimateMyWrote() {
        try {
            List<EstimateDto> estimateList = estimateService.estimateMyWrote();

            // 리스트가 비어 있지 않으면 200 상태 코드와 함께 반환
            if (!estimateList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(estimateList);
            } else {
                // 비어 있으면 404 상태 코드와 함께 메시지 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(estimateList);
            }
        } catch (Exception e) {
            // 예외 발생 시 500 상태 코드와 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // 에러 발생 시 응답을 null로 처리
        }
    }
    // 견적글 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> estimateDelete(@RequestParam int estno) {
        try{
            boolean result = estimateService.estimateDelete(estno);

            if(result){
                // 성공: 200 상태코드와 함께 삭제 완료 메시지 반환
                return ResponseEntity.status(HttpStatus.OK).body("Estimate Delete success.");
            } else {
                // 삭제 실패: 400 상태코드와 함께 실패 메시지 반환
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete");
            }
        } catch (Exception e) {
            // 예외 발생 시: 500 상태코드와 오류 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting estimate: " + e.getMessage());
        }
    }
}
