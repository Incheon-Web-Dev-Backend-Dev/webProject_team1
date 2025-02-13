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


    // 요청서에 해당하는 견적서 전체 출력 (일반 의뢰인이 올린)
    @GetMapping("/estimate/findall")
    public String estimateFindAll(){
        return "/estimate/findall.html";
    }



    //============================JobOffer============================
    // 구인글 쓰기(업체)
    @GetMapping("/job/write")
    public String jobWrite(){
        return "/job/write.html";
    }

    // 구인글 리스트(업체/개인수납가)
    @GetMapping("/job/list")
    public String jobList(){
        return "/job/list.html";
    }

    // 구인글 보기(업체/개인수납가)
    @GetMapping("/job/view")
    public String jobView(){
        return "/job/view.html";
    }

}
