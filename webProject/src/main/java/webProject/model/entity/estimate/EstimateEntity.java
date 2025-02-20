package webProject.model.entity.estimate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import webProject.model.dto.estimate.EstimateDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;

@Entity
@Table(name ="service_estimate")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder@ToString
@DynamicInsert // @columnDefault 쓸 경우 넣어야되는 어노테이션
public class EstimateEntity extends BaseTime {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int estno; // 견적서 번호

    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String esttitle; // 견적서 제목

    @Column(columnDefinition = "longtext", nullable = false)
    private String estcontent; // 견적서 내용

    @Column(columnDefinition = "varchar(30)", nullable = false)
    private String estcash; // 견적 금액

    @Column( columnDefinition = "boolean")
    @ColumnDefault("false")
    private boolean eststate; // 견적 채택 상태 ture: 채택 , false : 미채택

    @ManyToOne
    @JoinColumn(name = "mno", nullable = true) // 요청자 회원번호
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "reqno", nullable = true) // 요청서 번호
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
                .reqstate(this.requestEntity.isReqstate())
                .memail(this.memberEntity.getMemail())
                .mname(this.memberEntity.getMname())
                .cdate(this.getCdate().toString())
                .build();
    }

}

