package webProject.model.entity.like;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import webProject.model.dto.like.LikeDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.job.JobOfferEntity;
import webProject.model.entity.member.MemberEntity;

@Entity
@Table(name = "like_job")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder@ToString
public class LikeEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lno; // 좋아요 번호

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mno") // 누가 눌렀는지
    private MemberEntity memberEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jono") // 누른 게시글
    private JobOfferEntity jobOfferEntity;

    public LikeDto toLDto(){
        return LikeDto.builder()
                .lno(this.lno)
                .mno(this.memberEntity.getMno())
                .jono(this.jobOfferEntity.getJono())
                .mname(this.memberEntity.getMname())
                .cdate(this.getCdate().toString())
                .build();
    }
}
