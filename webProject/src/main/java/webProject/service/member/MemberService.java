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
import webProject.model.repository.estimate.EstimateRepository;
import webProject.model.repository.job.JobOfferRepository;
import webProject.model.repository.like.LikeRepository;
import webProject.model.repository.member.MemberFileRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.request.RequestRepository;
import webProject.model.repository.review.ReviewRepository;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberFileService memberFileService;
    @Autowired
    MemberFileRepository memberFileRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    EstimateRepository estimateRepository;
    @Autowired
    JobOfferRepository jobOfferRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    LikeRepository likeRepository;


    @Transactional
    //1. 회원가입
    public boolean signup(MemberDto memberDto) {
        try {
            // 회원 정보 저장
            MemberEntity memberEntity = memberDto.toEntity();
            MemberEntity saveEntity = memberRepository.save(memberEntity);

            if (saveEntity.getMno() <= 0) return false;

            // 파일 저장 (여러 개)
            List<MultipartFile> uploadFiles = memberDto.getUploadFile();
            if (!uploadFiles.isEmpty()) {
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

            if (!uploadFile.isEmpty()) {
                String filename2 = memberFileService.fileUpload(uploadFile);
                profileFileDto.setProfile(filename2);
            }

            MemberFileEntity profileFileEntity = profileFileDto.toEntity();
            System.out.println(profileFileDto);
            profileFileEntity.setMemberEntity(memberEntity);
            memberFileRepository.save(profileFileEntity);
            System.out.println(profileFileEntity);

            return true;
        } catch(Exception e){System.out.println(e);return false;}
    }


// ===================== 세션 관련 함수 ============== //
@Autowired
private HttpServletRequest request;

public boolean setSession(String memail) {
    HttpSession httpSession = request.getSession();
    httpSession.setAttribute("loginId", memail);
    return true;
}

// 로그인 함수
@Transactional // 트랜잭션
public boolean login(MemberDto memberDto) {
    boolean result = memberRepository.existsByMemailAndMpwd(memberDto.getMemail(), memberDto.getMpwd());

    if (result == true) {
        System.out.println("로그인성공!");
        setSession(memberDto.getMemail());
        return true;
    } else {
        System.out.println("로그인실패");
        return false;
    }
}

// 세션객체내 정보 반환 : 세션객체에 로그인된 회원아이디 반환하는 함수 ( 내정보 조회 , 수정 등등 )
public String getSession() {
    // (2)
    HttpSession httpSession = request.getSession();
    // (4) 세션 객체에 속성명의 값 반환한다. * 반환타입이 Object 이다.
    Object object = httpSession.getAttribute("loginId");
    // (5) 검사후 타입변환
    if (object != null) {// 만약에 세션 정보가 존재하면
        String memail = (String) object; // Object타입 --> String타입
        return memail;
    }
    return null;
}

//  세션객체내 정보 초기화 : 로그아웃
public boolean deleteSession() {
    HttpSession httpSession = request.getSession(); // (2)
    // (3) 세션객체 안에 특정한 속성명 제거
    httpSession.removeAttribute("loginId");
    return true;
}

// 현재 로그인된 회원의 회원정보 조회
public MemberDto getMyInfo() {
    String memail = getSession();  // 1. 현재 세션에 저장된 회원 아이디 조회
    if (memail != null) {   // 2. 만약에 로그인상태이면
        MemberEntity memberEntity = memberRepository.findByMemail(memail);  // 3. 회원아이디로 엔티티 조회
        MemberDto memberDto = memberEntity.toDto(); // 4. entity --> dto 변환
        return memberDto;// 5. 반환
    }
    return null; // * 비로그인상태이면
}
// [7] 현재 로그인된 회원 탈퇴
@Transactional
public boolean deleteMember() {
    MemberDto memberDto = getMyInfo();
    // 1. mno가 참조된곳에 모두 Null로 변경
    estimateRepository.unlinkMember(memberDto.getMno());
    reviewRepository.unlinkMember(memberDto.getMno());
    likeRepository.unlinkMember(memberDto.getMno());
    requestRepository.unlinkMember(memberDto.getMno());
    jobOfferRepository.unlinkMember(memberDto.getMno());

    // 2. 회원파일도 삭제
    memberFileRepository.deleteByMemberEntity_Mno(memberDto.getMno());
    // 3. 회원 삭제
    memberRepository.deleteById(memberDto.getMno());
    deleteSession();
    return true;
}
// 현재 로그인된 회원 정보 수정 ,  memail 이메일
@Transactional
public boolean myUpdate(MemberDto memberDto) {
    String memail = getSession();
    if (memail != null) {
        MemberEntity memberEntity = memberRepository.findByMemail(memail);
        memberEntity.setMemail(memberDto.getMemail());
        return true;
    }
    return false;
}

public boolean chechpwd(String mpwd){
    String memail = getSession();  // 1. 현재 세션에 저장된 회원 아이디 조회
    if (memail != null) {   // 2. 만약에 로그인상태이면
        MemberEntity memberEntity = memberRepository.findByMemail(memail);  // 3. 회원아이디로 엔티티 조회
        if (memberEntity.getMpwd() == mpwd)return true;
    }
    return false;
}

public boolean isEmailDuplicate(String email) {
    // 이메일이 존재하면 true 반환, 존재하지 않으면 false 반환
    return memberRepository.existsByMemail(email);
}
}
