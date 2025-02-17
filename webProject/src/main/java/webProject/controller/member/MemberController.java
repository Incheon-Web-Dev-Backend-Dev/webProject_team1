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
        return memberService.signup(memberDto);
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
    //8. 이메일 중복 확인
    @GetMapping("/member/checkemail.do")
    public boolean isEmailDuplicate(@RequestParam String email) {
        // 이메일 중복 확인 로직
        return memberService.isEmailDuplicate(email);
    }


    // 지명 Test - talend에서 true 받아옴. 근호씨 쓰세요
    @DeleteMapping("/member/deleteJM.do")
    public boolean deleteMember(int mno){
        return memberService.deleteMember(mno);
    }
}
