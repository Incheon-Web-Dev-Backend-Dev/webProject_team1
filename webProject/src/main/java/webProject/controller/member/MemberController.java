package webProject.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.member.MemberEntity;
import webProject.service.job.JobOfferService;
import webProject.service.member.MemberService;

import java.util.Map;

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
    public boolean login(@RequestBody MemberDto memberDto){return memberService.login(memberDto);}
    //3. 현재 로그인된 회원 아이디 http
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
    public boolean deleteMember(){return memberService.deleteMember();}
    //7. 회원정보 수정
    @PutMapping("/member/update.do")
    public boolean myUpdate( @RequestBody MemberDto memberDto ){
        return memberService.myUpdate( memberDto );
    }
    //8. 이메일 중복 확인
    @GetMapping("/member/checkemail.do")
    public boolean isEmailDuplicate(@RequestParam String email) {return memberService.isEmailDuplicate(email);}
    //9. 회원정보수정시 비밀번호확인
    // ✅ 9. 회원정보 수정 시 비밀번호 확인 (GET → POST 변경)
    @PostMapping("/member/checkpwd.do")  // ✅ GET → POST 변경
    public boolean checkPwd(@RequestBody Map<String, String> request) {
        String mpwd = request.get("mpwd");  // ✅ JSON Body에서 비밀번호 받기
        return memberService.chechpwd(mpwd);
    }


}
