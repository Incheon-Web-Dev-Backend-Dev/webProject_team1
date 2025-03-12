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
    private String reqspace;// 정리 수납 위치(ex 방, 거실 등)
    private String raddress; // 주소
    private double latitude; // 위도
    private double longitude; // 경도
    private boolean reqstate; // 요청서 상태 (true: 활성화 되어있음(마감은안됨), false: 비활성화(마감됨))
    private int reqrole; // 요청하고싶은 상대 정보 1:업체, 2:개인(전문가)
    private String reqdatetime;
    private long estimateCount; // 요청글에 들어오는 견적서 수
    private String deadLineTime; // 요청서 마감시간
    private double distance; // == 사용자 위치에 따른 거리 필드 / db 저장할게 아니므로 엔티티는 생략

    private int mno; // 요청서 작성한 회원번호
    private String mname; // 요청서 작성한 회원이름

    public RequestEntity toEntity() {
        return RequestEntity.builder()
                .reqno(this.reqno)
                .reqcontent(this.reqcontent)
                .reqtitle(this.reqtitle)
                .reqspace(this.reqspace)
                .raddress(this.raddress)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .reqstate(true)
                .reqrole(this.reqrole)
                .build();
    }

}
