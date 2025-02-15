package webProject.model.entity.like;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import webProject.model.dto.like.LikeDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.job.JobOfferEntity;
import webProject.model.entity.member.MemberEntity;

@Entity
@Table(name = "like_job")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder@ToString
@DynamicInsert // @columnDefault 쓸 경우 넣어야되는 어노테이션
public class LikeEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lno; // 좋아요 번호

    @Column(columnDefinition = "boolean", nullable = false)
    private boolean lstate; // 좋아요(지원여부) 상태 true : 지원했음, false : 지원하지않았음

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
