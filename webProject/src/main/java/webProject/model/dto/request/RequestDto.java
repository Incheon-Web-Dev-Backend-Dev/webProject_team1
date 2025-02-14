package webProject.model.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import webProject.model.entity.request.RequestEntity;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private int reqno;
    private String reqtitle;
    private String reqcontent;
    private String reqspace;
    private String reqbigarea;
    private String reqsmallarea;
    private boolean reqstate;
    private int reqroll; // 요청하고싶은 상대 정보 1:업체, 2:개인(전문가)
    private String reqdatetime;
    private long estimateCount; // 요청글에 들어오는 견적서 수

    private int mno; // 요청서 작성한 회원번호

    public RequestEntity toEntity() {
        return RequestEntity.builder()
                .reqno(this.reqno)
                .reqcontent(this.reqcontent)
                .reqtitle(this.reqtitle)
                .reqspace(this.reqspace)
                .reqspace(this.reqspace)
                .reqbigarea(this.reqbigarea)
                .reqsmallarea(this.reqsmallarea)
                .reqstate(this.reqstate)
                .reqroll(this.reqroll)
                .build();
    }

}
