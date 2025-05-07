package webProject.model.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Component;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.BaseTime;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private String reqspace; // 정리 수납 위치(ex 방, 거실 등)

    @Column(columnDefinition = "varchar(255)", nullable = false) // address 추가
    private String raddress; // 주소

    @Column(columnDefinition = "double", nullable = false)
    private double latitude; // 위도

    @Column(columnDefinition = "double", nullable = false)
    private double longitude; // 경도

    @Column(columnDefinition = "int", nullable = false)
    private int reqrole;

    @Column(columnDefinition = "boolean")
    @ColumnDefault("true")
    private boolean reqstate;// 요청서 상태 (true: 활성화 되어있음(마감은안됨), false: 비활성화(마감됨))

    @ManyToOne
    @JoinColumn(name= "mno", nullable = true)
    private MemberEntity memberEntity;

    // 양방향 관계를 위한 추가
    @OneToMany(mappedBy = "requestEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude // ToString 에서 제외, = 무한 순한 참조 발생 가능성
    @Builder.Default
    @JsonIgnore // JSON 으로 변환시 제외
    private List<EstimateEntity> estimateEntities = new ArrayList<>();


    // 1. 요청서 마감처리에 관한 함수
    public boolean isDeadlineReached() {
        // getCdate가 null인 경우 체크
        if (this.getCdate() == null) {
            System.out.println("요청글 생성 시간이 null입니다.");
            return false; // 또는 기본값 설정
        }

        // 마감기한(요청글 작성 후 7일) 계산
        LocalDateTime deadline = this.getCdate().plusDays(7);
        LocalDateTime now = LocalDateTime.now();

        // 디버깅 로그
        System.out.println("요청글 ID: " + this.reqno);
        System.out.println("요청글 생성 시간: " + this.getCdate());
        System.out.println("마감 기한: " + deadline);
        System.out.println("현재 시간: " + now);
        System.out.println("마감 여부: " + now.isAfter(deadline));
        System.out.println("reqstate" + this.reqstate);

        // deadline을 지나면 true 반환, 이전이면 false 반환
        return now.isAfter(deadline);
    }

    // 2. 요청서 마감시간 계산에 관한 함수
    public String calculatedDeadline() {
        LocalDateTime deadline = this.getCdate().plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return deadline.format(formatter);
    }


    public RequestDto toDto(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // 날짜 저장 형식을 년-월-일 시-분 으로 수정
        return RequestDto.builder()
                .reqno(this.reqno)
                .reqcontent(this.reqcontent)
                .reqspace(this.reqspace)
                .reqtitle(this.reqtitle)
                .reqspace(this.reqspace)
                .raddress(this.raddress)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .reqstate(!this.isDeadlineReached() && this.reqstate)  //채택 되거나 요청글기한이 마감되거나
                .reqrole(this.reqrole)
                .deadLineTime(this.calculatedDeadline())
                .mno(this.memberEntity.getMno())
                .mname(this.memberEntity.getMname())
                .reqdatetime(this.getCdate().format(formatter))
                .build();

    }


}
