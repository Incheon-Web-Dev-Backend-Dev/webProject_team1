package webProject.model.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import webProject.model.entity.request.RequestEntity;
import webProject.model.entity.request.RequestFileEntity;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestFileDto {

    private int reqfno;
    private String reqfname;
    private String reqfcdate;
    private String reqfudate;

    private int reqno; // fk 파일이 해당하는 요청서 번호

    public RequestFileEntity toEntity(){
        return RequestFileEntity.builder()
                .reqfno(this.reqfno)
                .reqfname(this.reqfname)
                .build();
    }
}
