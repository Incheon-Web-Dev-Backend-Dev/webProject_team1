package webProject.service.review;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ReviewFileService {
    // 절대경로 -> 프로퍼티 값 으로 변경
    @Value("${file.upload.base-path}")
    private String basePath;

    @Value("${file.upload.review-dir}")
    private String reviewDir;

    // 1. 업로드 함수
    public String reviewFileUpload(MultipartFile multipartFile) {
        System.out.println("원본 파일명: " + multipartFile.getOriginalFilename()); // 디버깅용
        System.out.println("속성명: " + multipartFile.getName()); // 디버깅용
        System.out.println("파일 크기: " + multipartFile.getSize()); // 디버깅용
        System.out.println("파일 존재 여부: " + multipartFile.isEmpty()); // 디버깅용

        // 1. UUID 조합
        String uuid = UUID.randomUUID().toString();
        String originalFilename = multipartFile.getOriginalFilename().replaceAll("_", "-");
        String filename = uuid + "_" + originalFilename;

        // 2. 전체 저장 경로 조합
        String savePath = basePath + File.separator + reviewDir;
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs(); // 폴더 없으면 생성
        }

        // 3. 파일 저장
        File file = new File(savePath + File.separator + filename);

        try {
            multipartFile.transferTo(file);
            System.out.println("리뷰 이미지 업로드 성공: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("업로드 실패: " + e.getMessage());
            return null;
        }

        return filename;
    }
}
