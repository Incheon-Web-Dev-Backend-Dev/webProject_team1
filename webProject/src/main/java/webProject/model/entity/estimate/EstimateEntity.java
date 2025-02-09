package webProject.model.entity.estimate;

import jakarta.persistence.*;
import lombok.*;
import webProject.model.dto.estimate.EstimateDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;

@Entity
@Table(name ="service_estimate")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder@ToString
public class EstimateEntity extends BaseTime {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int estno; // 견적서 번호

    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String esttitle; // 견적서 제목

    @Column(columnDefinition = "longtext", nullable = false)
    private String estcontent; // 견적서 내용

    @Column(columnDefinition = "varchar(30)", nullable = false)
    private String estcash; // 견적 금액

    @Column( columnDefinition = "boolean default false")
    private boolean eststate; // 견적 체택 상태

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mno") // 요청자 회원번호
    private MemberEntity memberEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reqno") // 요청서 번호
    private RequestEntity requestEntity;

    public EstimateDto toESDto(){
        return EstimateDto.builder()
                .estno(this.estno)
                .esttitle(this.esttitle)
                .estcontent(this.estcontent)
                .estcash(this.estcash)
                .eststate(this.eststate)
                .mno(this.memberEntity.getMno())
                .reqno(this.requestEntity.getReqno())
                .memail(this.memberEntity.getMemail())
                .mname(this.memberEntity.getMname())
                .cdate(this.getCdate().toString())
                .build();
    }

}

