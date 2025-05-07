package webProject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.request.RequestDto;
import webProject.service.request.RequestService;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    // 현재 로그인된 회원의 요청글 전체조회
    @GetMapping("/findall.do")
    public ResponseEntity<?> requestFindAll() {
        try{
            List<RequestDto> result = requestService.requestFindAll();
            if(result == null) { // request 리스트가 없고
                // 로그인 정보도 없으면
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("requestFindAll : please login");
            } else if (result.isEmpty()) {// 로그인은 했지만 request 리스트가 비어있으면
                return ResponseEntity.status(HttpStatus.OK).body("request.findAll status OK : review list null");
            } else {
                return  ResponseEntity.status(HttpStatus.OK).body(result);
            } // if-else end
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("review.findAll status error" + e.getMessage());
        } //try-catch end
    } // requestFindAll end

    // 현재 로그인된 회윈의 요청글 개별조회
    @GetMapping("/find.do")
    public ResponseEntity<?> requestFind(@RequestParam int reqno) {
        try{
            RequestDto result = requestService.requestFind(reqno);
            if(result == null) { // request 리스트가 없고
                // 로그인 정보도 없으면
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("requestFindAll : please login");
            } else {
                return  ResponseEntity.status(HttpStatus.OK).body(result);
            } // if-else end
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("review.findAll status error" + e.getMessage());
        } //try-catch end
    } // requestFindAll end

    // 견적 요청글 작성
    @PostMapping("/post.do")
    public ResponseEntity<?> requestPost(@RequestBody RequestDto requestDto){
        try{
            boolean result = requestService.requestPost(requestDto);
            if(result) {
                // 성공 : 201 created 상태코드에 대한 반환 내용
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                // 서비스 로직은 성공했지만 리뷰 등록은 실패했을 때 반환 내용
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request.post status BAD");
            }// if- else end
        } catch(Exception e) {
            // 서버 내부 오류 : 500 상태코드에 대한 반환 내용
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request.post status error" + e.getMessage());
        }// try-catch end
    }// reviewWrite end

    // ========== 위치에 따른 요청서 ==========

    // 요청글 보는 사용자 현재 위치 받기
    @PostMapping("/location")
    public ResponseEntity<?> setUserLocation(@RequestBody LocationRequest userLocation) {
        try{
            boolean result = requestService.setUserLocation(userLocation.getLatitude(), userLocation.getLongitude());
            if(result) {
                System.out.println("Received Latitude: " + userLocation.getLatitude());
                System.out.println("Received Longitude: " + userLocation.getLongitude());
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // GET 요청으로 거리 계산된 요청서 리스트 가져오기
    @GetMapping("/near")
    public ResponseEntity<List<RequestDto>> getNearRequests() {
        try{
            List<RequestDto> requestDtoList = requestService.getNearRequests();

            if(!requestDtoList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(requestDtoList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(requestDtoList);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // 페이징 관련
    @GetMapping("/paginated")
    public ResponseEntity<?> getPagedRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "reqno") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        try {
            Page<RequestDto> result = requestService.getPagedRequests(page, size, sort, direction);

            if (result == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login to view requests");
            } else if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("No requests found");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching paginated requests: " + e.getMessage());
        }
    }
} // class End

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class LocationRequest {
    private double latitude;
    private double longitude;
}
