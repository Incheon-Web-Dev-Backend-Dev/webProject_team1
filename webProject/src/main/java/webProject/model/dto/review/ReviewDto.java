package webProject.model.dto.review;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import webProject.model.entity.review.ReviewEntity;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {


    private int revno; // 이용후기 식별번호 pk
    private String revcontent; // 이용후기 내용
    private int revstar; // 이용후기 별점
    private List<String> revimg; // 이용후기 사진
    private String revcdate; //후기 작성 시간
    private String requdate; //후기 수정 시간

    private List<MultipartFile> uploadReviewFiles; // 업로드 파일 객체

    //fk
    private int mno; // 이용후기 작성하는 회원번호
    private int estno; // 이용후기 작성되는 견적서 번호

    public ReviewEntity toEntity() {
        return ReviewEntity.builder()
                .revno(this.revno)
                .revcontent(this.revcontent)
                .revstar(this.revstar)
                .revimg(this.revimg)
                .build();
    }

}
