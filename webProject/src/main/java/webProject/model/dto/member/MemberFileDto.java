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
    private String mfname; //파일이름
    private String profile; //프로필파일이름
    private String cdate; // 파일생성일

    public MemberFileEntity toEntity(){
        return MemberFileEntity.builder()
                .mfno(this.mfno)
                .mfname(this.mfname)
                .profilename(this.profile)
                .build();
    }
}
