package webProject.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.member.MemberFileDto;
import webProject.service.member.MemberService;
import webProject.service.member.MemberFileService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberFileService memberFileService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@ModelAttribute MemberDto memberDto,
                                         @RequestParam(required = false) MultipartFile uploadFile,
                                         @RequestParam(required = false) MultipartFile profile) {
        try {
            // 회원 정보 저장 먼저 진행 (MemberService에서 트랜잭션 처리)
            boolean result = memberService.signup(memberDto);

            if (!result) {
                return ResponseEntity.status(400).body("회원가입 실패");
            }

            return ResponseEntity.ok("가입 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody MemberDto memberDto) {
        boolean result = memberService.login(memberDto);
        return result ? ResponseEntity.ok(true) : ResponseEntity.status(401).body(false);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout() {
        boolean result = memberService.deleteSession();
        return result ? ResponseEntity.ok(true) : ResponseEntity.status(400).body(false);
    }

    // 현재 로그인된 회원 아이디
    @GetMapping("/login/id")
    public ResponseEntity<String> loginId() {
        String email = memberService.getSession();
        return email != null ? ResponseEntity.ok(email) : ResponseEntity.status(401).body("로그인 실패");
    }

    // 현재 로그인된 회원 정보 조회
    @GetMapping("/myinfo")
    public ResponseEntity<MemberDto> myInfo() {
        MemberDto memberDto = memberService.getMyInfo();
        return memberDto != null ? ResponseEntity.ok(memberDto) : ResponseEntity.status(404).body(null);
    }

    // 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteMember() {
        boolean result = memberService.deleteMember();
        return result ? ResponseEntity.ok(true) : ResponseEntity.status(400).body(false);
    }

    // 회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<Boolean> myUpdate(@RequestBody MemberDto memberDto) {
        boolean result = memberService.myUpdate(memberDto);
        return result ? ResponseEntity.ok(true) : ResponseEntity.status(400).body(false);
    }

    // 이메일 중복 확인
    @GetMapping("/checkemail")
    public ResponseEntity<Boolean> isEmailDuplicate(@RequestParam String email) {
        boolean isDuplicate = memberService.isEmailDuplicate(email);
        return ResponseEntity.ok(isDuplicate);
    }

    // 회원정보 수정시 비밀번호 확인
    @PostMapping("/checkpwd")
    public ResponseEntity<Boolean> checkPwd(@RequestBody Map<String, String> request) {
        String mpwd = request.get("mpwd");
        boolean isCorrect = memberService.chechpwd(mpwd);
        return isCorrect ? ResponseEntity.ok(true) : ResponseEntity.status(400).body(false);
    }
    /** 파일 관련 !!!! **/
    // 프로필 사진 업로드
    @PostMapping("/upload/profile")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = memberFileService.uploadProfile(file);
            return fileName != null ?
                    ResponseEntity.ok(fileName) :
                    ResponseEntity.status(400).body("파일 업로드 실패");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }

    // 추가적인 파일 업로드(업체 관련 파일 등)
    @PostMapping("/upload/company")
    public ResponseEntity<String> uploadCompanyImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = memberFileService.uploadCompany(file);
            return fileName != null ?
                    ResponseEntity.ok(fileName) :
                    ResponseEntity.status(400).body("파일 업로드 실패");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }

    // 추가적인 파일 업로드(마스터 관련 파일 등)
    @PostMapping("/upload/master")
    public ResponseEntity<String> uploadMasterImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = memberFileService.uploadMaster(file);
            return fileName != null ?
                    ResponseEntity.ok(fileName) :
                    ResponseEntity.status(400).body("파일 업로드 실패");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }

    // 프로필 이미지 조회 API
    @GetMapping("/profileimage/{mno}")
    public ResponseEntity<?> getProfileImage(@PathVariable int mno) {
        MemberFileDto profileImage = memberService.getProfileImage(mno);

        if (profileImage != null) {
            // 이미지 URL 추가
            String imageUrl = memberFileService.getFileUrl(profileImage.getProfile(), "profile");
            profileImage.setImageUrl(imageUrl);

            return ResponseEntity.ok(profileImage);
        } else {
            // 프로필 이미지가 없는 경우 기본 이미지 URL 반환
            return ResponseEntity.ok(
                    Map.of("imageUrl", "/img/default.png")
            );
        }
    }

    // 회원 첨부 파일 목록 조회 API
    @GetMapping("/files/{mno}")
    public ResponseEntity<?> getMemberFiles(@PathVariable int mno) {
        List<MemberFileDto> files = memberService.getMemberFiles(mno);
        return ResponseEntity.ok(files);
    }

    // 회원 첨부 파일 삭제 API
    @DeleteMapping("/file/delete/{mfno}")
    public ResponseEntity<Boolean> deleteFile(@PathVariable int mfno) {
        boolean result = memberFileService.deleteFile(mfno);
        return result
                ? ResponseEntity.ok(true)
                : ResponseEntity.status(400).body(false);
    }
}
