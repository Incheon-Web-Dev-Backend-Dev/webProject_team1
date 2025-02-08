package webProject.model.entity.request;

import jakarta.persistence.*;
import lombok.*;
import webProject.model.dto.request.RequestDto;
import webProject.model.dto.request.RequestFileDto;
import webProject.model.entity.BaseTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reqfile")
public class RequestFileEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rfno;

    @Column(columnDefinition = "varchar(50)", nullable = false, unique = true)
    private String rfname;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reqno")
    private RequestEntity requestEntity;

    public RequestFileDto toDto(){
        return RequestFileDto.builder()
                .rfno(this.rfno)
                .rfname(this.rfname)
                .rfcdate(this.getCdate().toString())
                .rfudate(this.getUdate().toString())
                .build();
    }


}
