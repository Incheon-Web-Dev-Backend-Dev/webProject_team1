package webProject.model.dto.member;

import lombok.*;
import webProject.model.entity.member.MemberEntity;

@Getter
@Setter
@ToString
@Builder // 룸복
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private int mno;
    private String memail;
    private String mpwd;
    private String mname;
    private String mphone;
    private String maddr;
    private String role;

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .mphone(this.mphone)
                .maddr(this.maddr)
                .role(this.role)
                .build();
    }

}
