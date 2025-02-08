package webProject.model.dto.job;

import lombok.*;
import webProject.model.entity.job.JobOfferEntity;

@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class JobOfferDto {

    private int jono; // 구인글 번호 PK
    private String jotitle; // 구인글 제목
    private String jocontent; // 구인글 내용
    private boolean jostate; // 구인글 마감상태
    private String joservice; // 구인하는 서비스
    private String jocity; // 구인하는 시
    private String jodistrict; // 구인하는 구

    private int mno; // 회원번호 FK

    public JobOfferEntity toEntity(){
        return JobOfferEntity.builder().jono(this.jono)
                .jotitle(this.jotitle).jocontent(this.jocontent)
                .jostate(this.jostate).joservice(this.joservice)
                .jocity(this.jocity).jodistrict(this.jodistrict)
                .build();
    }
}
