package webProject.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.member.MemberDto;
import webProject.service.job.JobOfferService;
import webProject.service.member.MemberService;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

   //1. 회원가입
    @PostMapping("/member/signup.do")
    public boolean signup(MemberDto memberDto){

        return memberService.sign(memberDto);
    }
    //2. 로그인
    @PostMapping("/member/login.do")
    public boolean login(@RequestBody MemberDto memberDto){

        return memberService.login(memberDto);
    }

    //3. 현재 로그인된 회원 아이디 http 패밍
    @GetMapping("/member/login/id.do")
    public String loginId( ){
        return memberService.getSession();
    }

    //4. 현재 로그인된 회원 로그아웃
    @GetMapping("/member/logout.do")
    public boolean logout(){
        return memberService.deleteSession();
    }
    //5. 현재 로그인된 회원 정보 조회
    @GetMapping("/member/myinfo.do")
    public MemberDto myInfo(){
        return memberService.getMyInfo();
    }
    //6. 회원 탈퇴
    @DeleteMapping("/member/delete.do")
    public boolean myDelete(){
        return memberService.myDelete();
    }

    //7. 회원정보 수정
    @PutMapping("/member/update.do")
    public boolean myUpdate( @RequestBody MemberDto memberDto ){
        return memberService.myUpdate( memberDto );
    }

}
