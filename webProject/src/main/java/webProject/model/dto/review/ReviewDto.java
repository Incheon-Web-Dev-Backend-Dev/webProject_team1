package webProject.model.dto.review;

import lombok.*;
import webProject.model.entity.review.ReviewEntity;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {


    private int revno; // 이용후기 식별번호 pk
    private String revcontent; // 이용후기 내용
    private int revstart; // 이용후기 별점
    private String revcdate; //후기 작성 시간
    private String requdate; //후기 수정 시간

    //fk
    private int mno; // 이용후기 작성하는 회원번호
    private int reqno; // 이용후기 작성되는 요청서 번호
    private int estno; // 이용후기 작성되는 견적서 번호

    public ReviewEntity toEntity() {
        return ReviewEntity.builder()
                .revno(this.revno)
                .revcontent(this.revcontent)
                .build();
    }

}
