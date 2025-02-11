package webProject.model.entity.job;

import jakarta.persistence.*;
import lombok.*;
import webProject.model.dto.job.JobFileDto;
import webProject.model.entity.BaseTime;

@Entity
@Table(name = "jobfile") // 구인글의 첨부파일 테이블
@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class JobFileEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jfno; // 구인글의 첨부파일 번호 PK

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String jfname; // 구인글의 첨부파일 이름


    @ManyToOne
    @JoinColumn(name = "jono")
    @ToString.Exclude
    private JobOfferEntity jobOfferEntity; // 구인글 번호 FK


    public JobFileDto toDto(){
        return JobFileDto.builder()
                .jfno(this.jfno).jfname(this.jfname)
                .jono(jobOfferEntity.getJono()).build();
    }


}
