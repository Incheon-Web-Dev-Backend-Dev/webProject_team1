package webProject.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    //메인
    @GetMapping("")
    public String index() {
        return "/index.html";
    }

    // ============== Member(선근호) =========================

    //회원가입 페이지
    @GetMapping("/member/signup")
    public String signup(){return "/member/signup.html";}
    //로그인 페이지
    @GetMapping("/member/login")
    public String login(){return "/member/login.html";}

    // ============== Request(유지명) =========================
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


    // 내가 올린 견적 요청서 상세보기 (일반 의뢰인)
    @GetMapping("/request/view")
    public String requestView() {
        return "/request/view.html";
    }





    //============================Estimate============================
    // 견적서 쓰기 (업체 / 개인수납가)
    @GetMapping("/estimate/write")
    public String estimateWrite(){return "/estimate/write.html";}


    // 요청서에 해당하는 견적서 전체 출력 (일반 의뢰인이 올린)
    @GetMapping("/estimate/list")
    public String estimateFindAll(){return "/estimate/list.html";}

    // 견적서 상세 조회
    @GetMapping("/estimate/view")
    public String estimateFind() {return "/estimate/view.html";}

    // 내가 작성한 견적서 전체 출력 (업체, 수납가)
    @GetMapping("/estimate/mywrote")
    public String estimateMyWrote(){return "/estimate/mywrote";}



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

    // 내가 쓴 구인글 리스트(업체)
    @GetMapping("/job/mylist")
    public String jobMylist(){return "/job/mylist.html";}

    // 구인글 수정(업체)
    @GetMapping("/job/update")
    public String jobUpdate(){return "/job/update.html";}

    // 지원자 리스트 보기(업체)
    @GetMapping("/job/like")
    public String jobLike(){return "/job/like.html";}

    //============================ My page ============================
    @GetMapping("/member/mypage")
    public String masterMyPage(){return "/member/mypage.html";}
    // 고객센터
    @GetMapping("/help/main")
    public String helpMainPage(){return "/help/main.html";}

    //============================ My page ============================
    // 관리자 페이지(메인)
    @GetMapping("/manage/main")
    public String manageMainPage(){return "/manage/main.html";}

    // 관리자 페이지 - 회원조회
    @GetMapping("/manage/memberlist")
    public String manageList(){return "/manage/memberlist.html";}

}
