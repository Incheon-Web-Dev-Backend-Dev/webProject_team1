package webProject.model.entity.member;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.member.MemberFileDto;
import webProject.model.entity.BaseTime;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "memberfile")
public class MemberFileEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private int mfno; //가입파일번호
    @Column(columnDefinition = "varchar(255)")
    private String mfname; //첨부파일이름
    @Column(columnDefinition = "varchar(255)")
    private String mprofilename; //프로필이름
    @ManyToOne  // FK
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    public MemberFileDto toDto(){
        return MemberFileDto.builder()
                .mfno(this.mfno)
                .mfname(this.mfname)
                .profile(this.mprofilename)
                .cdate(this.getCdate().toString())
                .build();
    }
}
