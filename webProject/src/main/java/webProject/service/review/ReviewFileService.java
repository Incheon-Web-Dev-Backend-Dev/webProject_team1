package webProject.service.review;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewFileService {
    // 서버 경로 내 이미지 폴더
    // 유지명 COM - 프로젝트 폴더 경로
    String uploadReviewFilePath = "/Users/jimyung/webProject_team1/build/resources/main/static/img/review/";

    // 1. 업로드 합수
    public List<String> reviewFileUpload(List<MultipartFile> multipartFiles){
        // 1. 업로드할 파일이 없으면 null 로 반환
        if(multipartFiles == null || multipartFiles.isEmpty()) {
            return null;
        }// if end

        // 2. 업로드할 파일의을 담아둘 객체 생성하고 반복문으로 업로드
        List<String> reviewFileNames = new ArrayList<>();
        for(int i = 0; i < multipartFiles.size() ; i++) {
            MultipartFile file = multipartFiles.get(i);
            if(file == null || file.isEmpty()) {
                continue;
            }// if end
            System.out.println(file.getOriginalFilename()); // 첨부파일명 확인

            // 3. UUID 생성
            String uuid = UUID.randomUUID().toString();

            // 4. 업로드 경로와 파일명 조합
            // uuid 구분을 위해 하이픈은 모두 언더바로 변경
            String reviewFileName = uuid + "-" + file.getOriginalFilename().replaceAll("-","_");
            String uploadReviewFile = uploadReviewFilePath +reviewFileName + reviewFileName;

            // 5. 조합된 경로로 file 클래스 객체 생성
            File saveFile = new File(uploadReviewFile);

            // 6. 지정된 경로로 업로드하기
            try{
                file.transferTo(saveFile);
                reviewFileNames.add(reviewFileName); // 업로드 성공한 파일명만 리스트에 추가
            } catch (Exception e) {
                System.out.println("reviewFile upload fail" + e.getMessage());
                return null;
            }// try-catch end

        } // for end

        return reviewFileNames;

    }// reviewFileUpload f end


}
