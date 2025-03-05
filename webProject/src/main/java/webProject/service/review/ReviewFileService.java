package webProject.service.review;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ReviewFileService {
    // 서버 경로 내 이미지 폴더
    // 유지명 COM - 프로젝트 폴더 경로
    String uploadReviewFilePath = "/Users/jimyung/webProject_team1/build/resources/main/static/img/review/";

    // 1. 업로드 합수
    public String reviewFileUpload(MultipartFile multipartFiles){
        // 1. 파일 이름을 식별 가능한 Uuid와 조합
        String uuid = UUID.randomUUID().toString();

        // 2. 조합
        String filename = uuid + "_" + multipartFiles.getOriginalFilename().replaceAll("_","-");
        // 3.
        File file = new File(uploadReviewFilePath + filename);

        // 4.
        try {
            multipartFiles.transferTo(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return filename;

    }// reviewFileUpload f end


}
