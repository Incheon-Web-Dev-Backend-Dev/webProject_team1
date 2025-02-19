package webProject.model.entity.request;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Component;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.member.MemberEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private int reqrole;

    @Column(columnDefinition = "boolean")
    @ColumnDefault("true")
    private boolean reqstate;

    @ManyToOne
    @JoinColumn(name= "mno", nullable = true)
    private MemberEntity memberEntity;

    // 1. 요청서 마감처리에 관한 함수
    public boolean isDeadlineReached() {
        // 마감기한(요청글 작성 후 7일) 이 지난 경우
        LocalDateTime deadline = this.getCdate().plusDays(7);
        // deadline을 지나면 true반환, 이전이면 false 반환
        return LocalDateTime.now().isAfter(deadline);
    }

    // 2. 요청서 마감시간 계산에 관한 함수
    public String calculatedDeadline() {
        LocalDateTime deadline = this.getCdate().plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return deadline.format(formatter);
    }


    public RequestDto toDto(){
        return RequestDto.builder()
                .reqno(this.reqno)
                .reqcontent(this.reqcontent)
                .reqspace(this.reqspace)
                .reqtitle(this.reqtitle)
                .reqspace(this.reqspace)
                .reqbigarea(this.reqbigarea)
                .reqsmallarea(this.reqsmallarea)
                .reqstate(this.reqstate || this.isDeadlineReached()) //채택 되거나 요청글기한이 마감되거나
                .reqrole(this.reqrole)
                .deadLineTime(this.calculatedDeadline())
                .mno(this.memberEntity.getMno())
                .mname(this.memberEntity.getMname())
                .reqdatetime(this.getCdate().toString())
                .build();

    }


}
