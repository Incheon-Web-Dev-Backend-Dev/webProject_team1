package webProject.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import webProject.service.review.ReviewFileService;

import java.util.List;

@RestController
public class ReviewFileController {

    @Autowired private ReviewFileService reviewFileService;

    // 1. 리뷰 첨부파일 업로드 매핑
    @PostMapping("/file/upload")
    public List<String> fileUpload(List<MultipartFile> multipartFile){
        return reviewFileService.reviewFileUpload(multipartFile);
    }
}
