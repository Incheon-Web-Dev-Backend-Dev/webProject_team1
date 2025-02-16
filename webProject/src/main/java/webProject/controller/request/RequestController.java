package webProject.controller.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.request.RequestDto;
import webProject.service.request.RequestService;

import java.security.PublicKey;
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
        RequestDto result = requestService.requestFind(reqno);
        System.out.println("조회 결과: " + result);
        return result;

        //return requestService.requestFind(reqno);
    }

    // 견적 요청글 작성
    @PostMapping("/post.do")
    public boolean requestPost (@RequestBody RequestDto requestDto){
        return requestService.requestPost(requestDto);
    }

    // 역할에 따른 요청서 나눠 보기
    @GetMapping("/findrole.do")
    public List<RequestDto> requestFindRoll(@RequestParam String role){return requestService.requestFindRoll(role);}
}
