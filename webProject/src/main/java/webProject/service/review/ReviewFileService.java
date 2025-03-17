package webProject.service.review;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ReviewFileService {
    // 서버 경로 내 이미지 폴더
    // 유지명 COM - 프로젝트 폴더 경로
    String uploadReviewFilePath = "/Users/jimyung/webProject_team1/webProject/build/resources/main/static/img/review/";
    // window -v
    //String uploadReviewFilePath = "D:\\sourcetree\\webProject\\build\\resources\\main\\static\\img\\review\\";

    // 1. 업로드 합수
    public String reviewFileUpload(MultipartFile multipartFiles){
        // 1. 매개변수로 MultipartFile 타입 객체를 받는다. 클라이언트가 보낸 첨부파일이 들어있는 객체
        System.out.println(multipartFiles.getOriginalFilename()); // 첨부파일의 파일명을 반환하는 함수
        System.out.println(multipartFiles.getName()); // 첨부파일의 속성명을 반환하는 함수
        System.out.println(multipartFiles.getSize()); // 첨부파일의 용량을 반환하는 함수 (byte)
        System.out.println(multipartFiles.isEmpty()); // 첨부파일의 존재여부를 반환하는 함수


        // 1. 파일 이름을 식별 가능한 Uuid와 조합
        String uuid = UUID.randomUUID().toString();

        // 2. 조합
        String filename = uuid + "_" + multipartFiles.getOriginalFilename().replaceAll("_","-");
        // 3. 조합된 경로로 file 클래스 객체 만들기
        File file = new File(uploadReviewFilePath + filename);

        // 4. 업로드하기(지정된 경로 transferTo)
        try {
            multipartFiles.transferTo(file);
            System.out.println("리뷰이미지 업로드 성공");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return filename;

    }// reviewFileUpload f end


}
