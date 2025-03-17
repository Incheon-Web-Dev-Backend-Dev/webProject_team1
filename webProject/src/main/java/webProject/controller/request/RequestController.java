package webProject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<RequestDto> requestFindAll() {
        return requestService.requestFindAll();
    }

    // 현재 로그인된 회윈의 요청글 개별조회
    @GetMapping("/find.do")
    public RequestDto requestFind(@RequestParam int reqno) {
        System.out.println("요청된 글번호: " + reqno);
        return requestService.requestFind(reqno);
    }

    // 견적 요청글 작성
    @PostMapping("/post.do")
    public boolean requestPost (@RequestBody RequestDto requestDto){
        return requestService.requestPost(requestDto);
    }

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
} // class End

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class LocationRequest {
    private double latitude;
    private double longitude;
}
