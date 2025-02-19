package webProject.model.dto.member;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import webProject.model.entity.member.MemberEntity;

import java.util.List;

@Getter
@Setter
@ToString
@Builder // 룸복
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private int mno;//회원번호
    private String memail;//회원 이메일
    private String mpwd;//회원 비밀번호
    private String mname;//회원 이름
    private String mphone;//회원핸드폰번호
    private String maddr;//회원주소
    private String role;//회원구분
    private boolean isapproved; //회원가입 혀가여부
    private String cdate;
    private String udate;
    private List<MultipartFile> uploadFile;
    private MultipartFile profile;


    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .mphone(this.mphone)
                .maddr(this.maddr)
                .isapproved(this.isapproved)
                .role(this.role)
                .build();
    }

}
