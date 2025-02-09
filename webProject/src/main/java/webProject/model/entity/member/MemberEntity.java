package webProject.model.entity.member;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.BaseTime;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity(name = "member")
public class MemberEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private int mno;
    @Column( nullable = false , unique = true , columnDefinition = "varchar(50)")
    private String memail;
    @Column( nullable = false ,columnDefinition = "varchar(16)")
    private String mpwd;
    @Column( nullable = false ,columnDefinition = "varchar(30)")
    private String mname;
    @Column( nullable = false ,columnDefinition = "varchar(13)")
    private String mphone;
    @Column( nullable = false ,columnDefinition = "varchar(255)")
    private String maddr;
    @Column( nullable = false ,columnDefinition = "varchar(30)")
    private String role;

    public MemberDto toDto(){
        return MemberDto.builder()
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
