package webProject.service.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    // 기본 프로필 이미지 이름
    private static final String DEFAULT_PROFILE_IMAGE = "default.png";

    @Transactional
    public boolean signup(MemberDto memberDto) {
        try {
            // 회원 정보 저장
            MemberEntity memberEntity = memberDto.toEntity();
            MemberEntity saveEntity = memberRepository.save(memberEntity);
            System.out.println(memberDto);
            if (saveEntity.getMno() <= 0) return false;

            String role = saveEntity.getRole(); // ROLE_USER, company, master 등

            // 파일 저장 (회사/마스터 용)
            List<MultipartFile> uploadFiles = memberDto.getUploadFile();
            if (uploadFiles != null && !uploadFiles.isEmpty()) {
                for (MultipartFile file : uploadFiles) {
                    if (file == null || file.isEmpty()) continue;

                    String fileName = null;
                    if (role.equals("company")) {
                        fileName = memberFileService.uploadCompany(file);
                    } else if (role.equals("master")) {
                        fileName = memberFileService.uploadMaster(file);
                    }
                    if (fileName != null) {
                        MemberFileDto memberFileDto = new MemberFileDto();
                        memberFileDto.setMfname(fileName);

                        MemberFileEntity memberFileEntity = memberFileDto.toEntity();
                        memberFileEntity.setMemberEntity(saveEntity);
                        memberFileRepository.save(memberFileEntity);
                    }
                }
            }

            // 프로필 사진 저장
            MemberFileDto profileFileDto = new MemberFileDto();
            MultipartFile uploadFile = memberDto.getProfile();

            if (uploadFile != null && !uploadFile.isEmpty()) {
                // 프로필 이미지가 제공된 경우
                String filename = memberFileService.uploadProfile(uploadFile);
                profileFileDto.setProfile(filename);
            } else {
                // 프로필 이미지가 없는 경우 기본 이미지 사용
                profileFileDto.setProfile(DEFAULT_PROFILE_IMAGE);
            }

            MemberFileEntity profileFileEntity = profileFileDto.toEntity();
            System.out.println(profileFileDto);
            profileFileEntity.setMemberEntity(saveEntity);
            memberFileRepository.save(profileFileEntity);
            System.out.println(profileFileEntity);

            return true;
        } catch(Exception e) {
            System.out.println(e);
            return false;
        }
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
    @Transactional
    public boolean login(MemberDto memberDto) {
        boolean result = memberRepository.existsByMemailAndMpwd(memberDto.getMemail(), memberDto.getMpwd());

        if (result) {
            System.out.println("로그인성공!");
            setSession(memberDto.getMemail());
            return true;
        } else {
            System.out.println("로그인실패");
            return false;
        }
    }

    public String getSession() {
        HttpSession httpSession = request.getSession();
        Object object = httpSession.getAttribute("loginId");
        if (object != null) {
            return (String) object;
        }
        return null;
    }

    public boolean deleteSession() {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("loginId");
        return true;
    }

    public MemberDto getMyInfo() {
        String memail = getSession();
        if (memail != null) {
            MemberEntity memberEntity = memberRepository.findByMemail(memail);
            return memberEntity.toDto();
        }
        return null;
    }

    @Transactional
    public boolean deleteMember() {
        MemberDto memberDto = getMyInfo();
        estimateRepository.unlinkMember(memberDto.getMno());
        reviewRepository.unlinkMember(memberDto.getMno());
        likeRepository.unlinkMember(memberDto.getMno());
        requestRepository.unlinkMember(memberDto.getMno());
        jobOfferRepository.unlinkMember(memberDto.getMno());

        memberFileRepository.deleteByMemberEntity_Mno(memberDto.getMno());
        memberRepository.deleteById(memberDto.getMno());
        deleteSession();
        return true;
    }

    @Transactional
    public boolean myUpdate(MemberDto memberDto) {
        String memail = getSession();
        if (memail != null) {
            MemberEntity memberEntity = memberRepository.findByMemail(memail);
            memberEntity.setMemail(memberDto.getMemail());
            memberEntity.setMpwd(memberDto.getMpwd());
            memberEntity.setMname(memberDto.getMname());
            memberEntity.setMphone(memberDto.getMphone());
            memberEntity.setMaddr(memberDto.getMaddr());
            return true;
        }
        return false;
    }

    public boolean chechpwd(String mpwd) {
        String memail = getSession();
        if (memail != null) {
            MemberEntity memberEntity = memberRepository.findByMemail(memail);
            if (memberEntity != null) {
                String storedPwd = memberEntity.getMpwd();
                return storedPwd != null && storedPwd.equals(mpwd);
            }
        }
        return false;
    }

    public boolean isEmailDuplicate(String email) {
        return memberRepository.existsByMemail(email);
    }

    /**
     * 회원의 프로필 이미지 정보를 조회합니다.
     * @param mno 회원 번호
     * @return 프로필 이미지 정보
     */
    public MemberFileDto getProfileImage(int mno) {
        MemberEntity memberEntity = memberRepository.findById(mno).orElse(null);
        if (memberEntity == null) return null;

        // 회원의 프로필 이미지 조회
        MemberFileEntity profileEntity = memberFileRepository.findByMemberEntityAndProfilenameIsNotNull(memberEntity);

        if (profileEntity != null) {
            return profileEntity.toDto();
        }

        return null;
    }

    /**
     * 회원의 첨부 파일 목록을 조회합니다.
     * @param mno 회원 번호
     * @return 첨부 파일 목록
     */
    public List<MemberFileDto> getMemberFiles(int mno) {
        MemberEntity memberEntity = memberRepository.findById(mno).orElse(null);
        if (memberEntity == null) return new ArrayList<>();

        // 회원의 첨부 파일 목록 조회 (프로필 이미지 제외)
        List<MemberFileEntity> fileEntities = memberFileRepository.findByMemberEntityAndMfnameIsNotNull(memberEntity);

        return fileEntities.stream()
                .map(MemberFileEntity::toDto)
                .collect(Collectors.toList());
    }
}