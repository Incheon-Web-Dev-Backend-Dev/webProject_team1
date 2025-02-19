package webProject.model.entity.member;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.BaseTime;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity(name = "member")
@DynamicInsert // @columnDefault 쓸 경우 넣어야되는 어노테이션
public class MemberEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private int mno; //회원번호 
    @Column( nullable = false , unique = true , columnDefinition = "varchar(50)")
    private String memail;//회원 이메일
    @Column( nullable = false ,columnDefinition = "varchar(16)")
    private String mpwd;//회원 비밀번호
    @Column( nullable = false ,columnDefinition = "varchar(30)")
    private String mname;//회원 이름
    @Column( nullable = false ,columnDefinition = "varchar(13)")
    private String mphone;//회원핸드폰번호
    @Column( nullable = false ,columnDefinition = "varchar(255)")
    private String maddr;//회원주소
    @Column( nullable = false ,columnDefinition = "varchar(30)")
    private String role;//회원구분
    @Column( nullable = false, columnDefinition = "boolean")
    @ColumnDefault("false")
    private boolean isapproved; // 회원가입 승인여부 true : 승인됨, false : 승인안됨

    public MemberDto toDto(){
        return MemberDto.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mname(this.mname)
                .mphone(this.mphone)
                .maddr(this.maddr)
                .isapproved(this.isapproved)
                .role(this.role)
                .cdate(this.getCdate().toString())
                .udate(this.getUdate().toString())
                .build();
    }
}
