package webProject.service.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.member.MemberFileDto;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.member.MemberFileEntity;
import webProject.model.repository.member.MemberFileRepository;
import webProject.model.repository.member.MemberRepository;

import java.util.List;

@Service
public class MemberService {
    @Autowired MemberRepository memberRepository;
    @Autowired MemberFileService memberFileService;
    @Autowired
    MemberFileRepository memberFileRepository;
    @Transactional
    //1. 회원가입
    public boolean signup(MemberDto memberDto){
        try {
            // 회원 정보 저장
            MemberEntity memberEntity = memberDto.toEntity();
            MemberEntity saveEntity = memberRepository.save(memberEntity);

            if (saveEntity.getMno() <= 0) return false;

            // 파일 저장 (여러 개)
            List<MultipartFile> uploadFiles = memberDto.getUploadFile();
            if (uploadFiles != null) {
                for (MultipartFile file : uploadFiles) {
                    String fileName = memberFileService.fileUpload(file);

                    MemberFileDto memberFileDto = new MemberFileDto();
                    memberFileDto.setMfname(fileName);

                    MemberFileEntity memberFileEntity = memberFileDto.toEntity();
                    memberFileEntity.setMemberEntity(memberEntity);
                    memberFileRepository.save(memberFileEntity);
                }
            }

            // 프로필 사진 저장
            MultipartFile uploadFile = memberDto.getProfile();
            MemberFileDto profileFileDto = new MemberFileDto();
            if (uploadFile != null) {
                String filename2 = memberFileService.fileUpload(uploadFile);
                profileFileDto.setProfile(filename2);
            } else {
                profileFileDto.setProfile("default.jpg");
            }

            MemberFileEntity profileFileEntity = profileFileDto.toEntity();
            profileFileEntity.setMemberEntity(memberEntity);
            memberFileRepository.save(profileFileEntity);

            return true;
        } catch (Exception e) {
            System.out.println("회원 가입 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
    // ===================== 세션 관련 함수 ============== //
    @Autowired private HttpServletRequest request;
    public boolean setSession(String memail){
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("loginId" , memail);
        return true;
    }
    @Transactional // 트랜잭션
    public boolean login( MemberDto memberDto ){
        boolean result = memberRepository.existsByMemailAndMpwd(memberDto.getMemail(),  memberDto.getMpwd() );

        if (result == true){
            System.out.println("로그인성공!");
            setSession(memberDto.getMemail());
            return true;
        }else {
            System.out.println("로그인실패");
            return false;
        }
    }

    // 세션객체내 정보 반환 : 세션객체에 로그인된 회원아이디 반환하는 함수 ( 내정보 조회 , 수정 등등 )
    public String getSession( ){
        // (2)
        HttpSession httpSession = request.getSession();
        // (4) 세션 객체에 속성명의 값 반환한다. * 반환타입이 Object 이다.
        Object object = httpSession.getAttribute( "loginId");
        // (5) 검사후 타입변환
        if( object != null ){// 만약에 세션 정보가 존재하면
            String memail = (String)object; // Object타입 --> String타입
            return memail;
        }
        return null;
    }

    //  세션객체내 정보 초기화 : 로그아웃
    public boolean deleteSession(){
        HttpSession httpSession = request.getSession(); // (2)
        // (3) 세션객체 안에 특정한 속성명 제거
        httpSession.removeAttribute( "loginId");
        return true;
    }

    // 현재 로그인된 회원의 회원정보 조회
    public MemberDto getMyInfo(){
        String memail = getSession();  // 1. 현재 세션에 저장된 회원 아이디 조회
        if( memail != null ){   // 2. 만약에 로그인상태이면
            MemberEntity memberEntity = memberRepository.findByMemail(  memail);  // 3. 회원아이디로 엔티티 조회
            MemberDto memberDto = memberEntity.toDto(); // 4. entity --> dto 변환
            return memberDto;// 5. 반환
        }
        return null; // * 비로그인상태이면
    }
    // 현재 로그인된 회원 탈퇴
    public boolean myDelete( ){
        String memail = getSession(); // 1. 현재 세션에 저장된 회원 아이디 조회
        if( memail != null ){// 2. 만약에 로그인상태이면
            MemberEntity memberEntity = memberRepository.findByMemail( memail ); // 3. 현재 로그인된 아이디로 엔티티 조회


            // 외래 키로 참조하고 있는 엔티티의 관계를 끊음

            memberRepository.delete( memberEntity ); // 4. 엔티티 탈퇴/삭제 하기
            deleteSession();// ** 로그인정보 지우기 : 로그아웃
            return true;// 5. 반환
        }
        return false; // * 비로그인상태이면
    }
    // 현재 로그인된 회원 정보 수정 , mname 닉네임 , memail 이메일
    @Transactional
    public boolean myUpdate( MemberDto memberDto ){
        String memail = getSession();
        if( memail != null ){
            MemberEntity memberEntity = memberRepository.findByMemail(memail );
            memberEntity.setMname( memberDto.getMname() );
            memberEntity.setMemail( memberDto.getMemail() );
            return true;
        }
        return false;
    }


}
