package webProject.model.entity.request;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Component;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.member.MemberEntity;

@Entity
@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name="service_request")
@DynamicInsert // @columnDefault 쓸 경우 넣어야되는 어노테이션
@Component
public class RequestEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reqno;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String reqtitle;

    @Column(columnDefinition = "longtext", nullable = false)
    private String reqcontent;

    @Column(columnDefinition = "varchar(30)", nullable = false)
    private String reqspace;

    @Column(columnDefinition = "varchar(30)", nullable = false)
    private String reqbigarea;

    @Column(columnDefinition = "varchar(30)", nullable = false)
    private String reqsmallarea;

    @Column(columnDefinition = "int", nullable = false)
    private int reqroll;

    @Column(columnDefinition = "boolean")
    @ColumnDefault("false")
    private boolean reqstate;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "mno")
    private MemberEntity memberEntity;

    public RequestDto toDto(){
        return RequestDto.builder()
                .reqno(this.reqno)
                .reqcontent(this.reqcontent)
                .reqspace(this.reqspace)
                .reqtitle(this.reqtitle)
                .reqspace(this.reqspace)
                .reqbigarea(this.reqbigarea)
                .reqsmallarea(this.reqsmallarea)
                .reqstate(this.reqstate)
                .reqroll(this.reqroll)
                .mno(this.memberEntity.getMno())
                .reqdatetime(this.getCdate().toString())
                .build();

    }


}
