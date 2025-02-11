package webProject.model.entity.request;

import jakarta.persistence.*;
import lombok.*;
import webProject.model.dto.request.RequestDto;
import webProject.model.dto.request.RequestFileDto;
import webProject.model.entity.BaseTime;

@Entity
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
    private int reqfno;

    @Column(columnDefinition = "varchar(50)", nullable = false, unique = true)
    private String reqfname;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reqno")
    private RequestEntity requestEntity;

    public RequestFileDto toDto(){
        return RequestFileDto.builder()
                .reqfno(this.reqfno)
                .reqfname(this.reqfname)
                .reqfcdate(this.getCdate().toString())
                .reqfudate(this.getUdate().toString())
                .build();
    }


}
