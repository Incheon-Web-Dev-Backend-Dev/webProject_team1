package webProject.model.dto.like;


import lombok.*;
import webProject.model.entity.like.LikeEntity;

@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder@ToString
public class LikeDto {
    private int lno;
    private boolean lstate; //좋아요(구직지원) 상태
    private int mno;
    private int jono;
    private String cdate;
    private String udate;
    // 좋아요 누른사람 이름
    private String mname;

    public LikeEntity toLEntity(){
        return LikeEntity.builder()
                .lno(this.lno)
                .lstate(this.lstate)
                .build();
    }
}
