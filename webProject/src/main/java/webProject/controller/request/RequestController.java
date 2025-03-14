package webProject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void setUserLocation(@RequestBody LocationRequest userLocation) {
        System.out.println("Received Latitude: " + userLocation.getLatitude());
        System.out.println("Received Longitude: " + userLocation.getLongitude());  // 여기가 0.0인지 확인
        requestService.setUserLocation(userLocation.getLatitude(), userLocation.getLongitude());
    }

    // GET 요청으로 거리 계산된 요청서 리스트 가져오기
    @GetMapping("/near")
    public List<RequestDto> getNearRequests() {
        // 거리 계산 후 가까운 요청서 리스트 반환
        return requestService.getNearRequests();
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