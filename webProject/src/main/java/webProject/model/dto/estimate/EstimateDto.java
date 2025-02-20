package webProject.model.dto.estimate;

import lombok.*;
import webProject.model.entity.estimate.EstimateEntity;

@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder@ToString
public class EstimateDto {
    private int estno;
    private String esttitle;
    private String estcontent;
    private String estcash;
    private boolean eststate;
    private int mno;
    private String memail;
    private String mname;
    private int reqno;
    private boolean reqstate;
    private String cdate;
    private String udate;

    public EstimateEntity toESEntity(){
        return EstimateEntity.builder()
                .estno(this.estno)
                .esttitle(this.esttitle)
                .estcontent(this.estcontent)
                .estcash(this.estcash)
                .eststate(this.eststate)
                .build();
    }
}
