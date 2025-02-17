package webProject.model.dto.member;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import webProject.model.entity.member.MemberFileEntity;

@Getter
@Setter
@ToString
@Builder // 룸복
@AllArgsConstructor
@NoArgsConstructor
public class MemberFileDto {
    private int mfno; //가입파일번호
<<<<<<< HEAD
    private String mfname; //파일이름
    private String mfname2; //파일이름2
=======
    private String mfname; // 첨부파일이름
    private String profile; //프로필파일이름
>>>>>>> 9de46a957f09dd0b0d8e717d55ecba7a09b9d9b4
    private String cdate; // 파일생성일



    public MemberFileEntity toEntity(){
        return MemberFileEntity.builder()
                .mfno(this.mfno)
                .mfname(this.mfname)
<<<<<<< HEAD
                .profilename(this.mfname2)
=======
                .mprofilename(this.profile)
>>>>>>> 9de46a957f09dd0b0d8e717d55ecba7a09b9d9b4
                .build();
    }
}
