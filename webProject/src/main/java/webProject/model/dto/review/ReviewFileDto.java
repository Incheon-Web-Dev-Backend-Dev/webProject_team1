package webProject.model.dto.review;

import lombok.*;
import webProject.model.entity.review.ReviewFileEntity;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFileDto {

    private int revfno; // 요청파일번호
    private String revfname;// 요청파일명
    private String revfcdate; // 요청파일생성날짜
    private String revfudate; // 요청파일수정날짜

    // fk
    private int revno; // 이용 후기글 번호

    public ReviewFileEntity toEntity() {
        return ReviewFileEntity.builder()
                .revfname(this.revfname)
                .revfno(this.revfno)
                .build();
    }
}
