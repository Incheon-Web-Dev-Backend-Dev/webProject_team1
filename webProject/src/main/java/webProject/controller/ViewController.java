package webProject.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("")
    public String index() {
        return "/index.html";
    }

    //회원가입 페이지
    @GetMapping("/member/signup")
    public String signup(){return "/member/signup.html";}
    //로그인 페이지
    @GetMapping("/member/login")
    public String login(){return "/member/login.html";}

    // 견적 요청서 쓰기 (일반 의뢰인)
    @GetMapping("/request/post")
    public String requestPost() {
        return "/request/post.html";
    }

    // 내가 올린 견적 요청서 리스트 (일반 의뢰인)
    @GetMapping("/request/list")
    public String requestList() {
        return "/request/list.html";
    }

    // 견적서 쓰기 (업체 / 개인수납가)
    @GetMapping("/estimate/write")
    public String estimateWrite(){
        return "/estimate/write.html";
    }

}
