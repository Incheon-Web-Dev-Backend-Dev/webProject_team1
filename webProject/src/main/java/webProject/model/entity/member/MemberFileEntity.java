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
<<<<<<< HEAD

    @Column(columnDefinition = "varchar(255)")
    private String mfname; //파일이름
    @Column(columnDefinition = "varchar(255)")
    private String profilename; //파일이름

=======
    @Column(columnDefinition = "varchar(255)")
    private String mfname; //첨부파일이름
    @Column(columnDefinition = "varchar(255)")
    private String mprofilename; //프로필이름
>>>>>>> 9de46a957f09dd0b0d8e717d55ecba7a09b9d9b4
    @ManyToOne  // FK
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    public MemberFileDto toDto(){
        return MemberFileDto.builder()
                .mfno(this.mfno)
                .mfname(this.mfname)
<<<<<<< HEAD
                .mfname2(this.profilename)
=======
                .profile(this.mprofilename)
>>>>>>> 9de46a957f09dd0b0d8e717d55ecba7a09b9d9b4
                .cdate(this.getCdate().toString())
                .build();
    }
}
a3438dcf-158f-467f-b915-1980f4e5694c-mainbanner3.jpg
6fcd4b2d-84b4-4902-b132-a0c879e4fd94-default.jpg