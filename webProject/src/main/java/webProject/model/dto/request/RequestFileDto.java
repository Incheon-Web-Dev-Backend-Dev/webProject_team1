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

    private int rfno;
    private String rfname;
    private String rfcdate;
    private String rfudate;

    private int reqno; // fk 파일이 해당하는 요청서 번호

    public RequestFileEntity toEntity(){
        return RequestFileEntity.builder()
                .rfno(this.rfno)
                .rfname(this.rfname)
                .build();
    }
}
