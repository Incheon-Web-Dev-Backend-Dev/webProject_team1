package webProject.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webProject.model.entity.member.MemberFileEntity;
import webProject.model.repository.member.MemberFileRepository;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MemberFileService {

    @Autowired
    private MemberFileRepository memberFileRepository;

    @Value("${file.upload.base-path}")
    private String uploadBasePath;

    @Value("${file.upload.profile-dir}")
    private String profileDir;

    @Value("${file.upload.company-dir}")
    private String companyDir;

    @Value("${file.upload.master-dir}")
    private String masterDir;

    // 업체, 마스터 파일 저장
    public String fileUpload(MultipartFile multipartFile, String folder) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            System.out.println("파일이 비어 있음");
            return null;
        }

        try {
            // 절대 경로 사용 (application.properties에서 설정)
            String uploadPath = Paths.get(uploadBasePath, folder).toString();
            System.out.println("업로드 경로: " + uploadPath);

            // 디렉토리가 없으면 생성
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                System.out.println("디렉토리 생성 결과: " + created);
                if (!created) {
                    System.out.println("디렉토리 생성 실패: " + uploadPath);
                    return null;
                }
            }

            // 업로드 파일 이름
            String uuid = UUID.randomUUID().toString();
            String originalFilename = multipartFile.getOriginalFilename();
            String fileName = uuid + "-" + (originalFilename != null ? originalFilename.replaceAll("-", "_") : "unnamed");

            // 전체 파일 경로
            File destFile = new File(uploadDir, fileName);

            // 파일 저장 (transferTo 사용)
            multipartFile.transferTo(destFile);

            System.out.println("파일 저장 완료: " + destFile.getAbsolutePath());
            System.out.println("웹 접근 경로: /uploads/" + folder + "/" + fileName);

            return fileName;
        } catch (Exception e) {
            System.out.println("파일 업로드 오류: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // 프로필 사진 저장
    public String uploadProfile(MultipartFile multipartFile) {
        return fileUpload(multipartFile, profileDir);
    }

    // 회사 이미지 저장
    public String uploadCompany(MultipartFile multipartFile) {
        return fileUpload(multipartFile, companyDir);
    }

    // 마스터 이미지 저장
    public String uploadMaster(MultipartFile multipartFile) {
        return fileUpload(multipartFile, masterDir);
    }

    /**
     * 첨부 파일을 삭제합니다.
     * @param mfno 첨부 파일 번호
     * @param fileName 파일 이름
     * @param role 역할 (company, master, profile)
     * @return 삭제 성공 여부
     */
    public boolean deleteFile(int mfno, String fileName, String role) {
        try {
            String folderPath;

            if (role.equals("company")) {
                folderPath = companyDir;
            } else if (role.equals("master")) {
                folderPath = masterDir;
            } else if (role.equals("profile")) {
                folderPath = profileDir;
            } else {
                return false; // 지원하지 않는 역할
            }

            Path filePath = Paths.get(uploadBasePath, folderPath, fileName);

            // 물리적 파일 삭제
            File file = filePath.toFile();
            if (file.exists()) {
                boolean deleted = file.delete();
                System.out.println("파일 삭제 " + (deleted ? "성공" : "실패") + ": " + filePath);
                return deleted;
            } else {
                System.out.println("삭제할 파일이 존재하지 않음: " + filePath);
                return true; // 파일이 존재하지 않아도 성공으로 간주
            }
        } catch (Exception e) {
            System.out.println("파일 삭제 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 첨부 파일을 삭제합니다. (MemberFileRepository에서 파일 정보 조회)
     * @param mfno 첨부 파일 번호
     * @return 삭제 성공 여부
     */
    @Transactional
    public boolean deleteFile(int mfno) {
        try {
            // 첨부 파일 정보 조회
            MemberFileEntity fileEntity = memberFileRepository.findById(mfno).orElse(null);
            if (fileEntity == null) return false;

            // 파일 타입과 이름 확인
            String fileName = null;
            String role = null;

            if (fileEntity.getMfname() != null) {
                fileName = fileEntity.getMfname();
                role = fileEntity.getMemberEntity().getRole();
            } else if (fileEntity.getProfilename() != null) {
                fileName = fileEntity.getProfilename();
                role = "profile";
            } else {
                return false; // 파일 이름이 없음
            }

            // 물리적 파일 삭제
            boolean fileDeleted = deleteFile(mfno, fileName, role);

            // DB에서 파일 정보 삭제
            if (fileDeleted) {
                memberFileRepository.deleteById(mfno);
                return true;
            }

            return false;
        } catch (Exception e) {
            System.out.println("파일 삭제 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 파일의 전체 URL 경로를 반환합니다.
     * @param fileName 파일 이름
     * @param folder 폴더 이름 (profile, company, master)
     * @return 웹에서 접근 가능한 URL 경로
     */
    public String getFileUrl(String fileName, String folder) {
        if (fileName == null || fileName.isEmpty()) {
            return "/img/default.png"; // 기본 이미지 경로
        }
        return "/uploads/" + folder + "/" + fileName;
    }
}