package webProject.model.entity.job;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.like.LikeEntity;
import webProject.model.entity.member.MemberEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "joboffer") // 기업에서 작성하는 구인글 테이블
@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
@DynamicInsert // @columnDefault 쓸 경우 넣어야되는 어노테이션
public class JobOfferEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jono; // 구인글 번호 PK

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String jotitle; // 구인글 제목

    @Column(columnDefinition = "longtext", nullable = false)
    private String jocontent; // 구인글 내용

    @Column(columnDefinition = "boolean", nullable = false)
    @ColumnDefault("false")
    private boolean jostate; // 구인글 마감상태 (글 작성시 기본 false, 마감되면 true)

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String joservice; // 구인하는 서비스

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String jocity; // 구인하는 시

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String jodistrict; // 구인하는 구




    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    @ToString.Exclude
    private MemberEntity memberEntity; // 회원번호 FK

//    @ToString.Exclude@Builder.Default
//    @OneToMany(mappedBy = "jobOfferEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<LikeEntity> likeEntityList = new ArrayList<>();



    public JobOfferDto toDto(){
        return JobOfferDto.builder().jono(this.jono)
                .jotitle(this.jotitle).jocontent(this.jocontent)
                .jostate(this.jostate).joservice(this.joservice)
                .jocity(this.jocity).jodistrict(this.jodistrict)
                .cdate(this.getCdate().toString())
                .mno(memberEntity.getMno())
                .memberDto(memberEntity.toDto()).build();
    }

}
