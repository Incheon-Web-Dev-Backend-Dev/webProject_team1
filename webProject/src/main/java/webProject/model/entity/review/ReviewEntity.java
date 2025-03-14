package webProject.model.entity.review;

import com.beust.ah.A;
import jakarta.persistence.*;
import lombok.*;
import webProject.model.dto.review.ReviewDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name= "estno", nullable = true)
    private EstimateEntity estimateEntity;

    // 양방향 설정
    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<ReviewFileEntity> reviewFileEntityList = new ArrayList<>();

    public ReviewDto toDto() {
        return ReviewDto.builder()
                .revno(this.revno)
                .revcontent(this.revcontent)
                .revcdate(this.getCdate().toString())
                .requdate(this.getUdate().toString())
                .revstar(this.revstar)
                .mno(this.memberEntity.getMno())
                .estno(this.estimateEntity.getEstno())
                .estWriterName(this.estimateEntity.getMemberEntity().getMname())
                .revimgList(
                        this.reviewFileEntityList.stream().map(
                                (imgEntity) -> {return imgEntity.getRevfname(); }
                                ).collect(Collectors.toList())
                )
                .build();
    }





}
