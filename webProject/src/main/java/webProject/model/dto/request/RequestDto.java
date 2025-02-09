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
    private String reqdatetime;

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
                .build();
    }

}
