package webProject.model.entity.review;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import webProject.model.dto.review.ReviewDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="review")
public class ReviewEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int revno;

    @Column(columnDefinition = "longtext", nullable = false)
    private String revcontent;

    @Column(columnDefinition = "int", nullable = false)
    private int revstar;

    @ManyToOne
    @JoinColumn(name= "mno", nullable = true)
    private MemberEntity memberEntity;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name= "reqno")
//    private RequestEntity requestEntity;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name= "estno", nullable = true)
    private EstimateEntity estimateEntity;

    public ReviewDto toDto() {
        return ReviewDto.builder()
                .revno(this.revno)
                .revcontent(this.revcontent)
                .revcdate(this.getCdate().toString())
                .requdate(this.getUdate().toString())
                .mno(this.memberEntity.getMno())
                //.reqno(this.requestEntity.getReqno())
                .estno(this.estimateEntity.getEstno())
                .build();
    }





}
