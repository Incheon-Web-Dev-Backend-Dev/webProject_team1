package webProject.model.dto.member;

import lombok.*;
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
    private String cdate; // 지급일


    public MemberFileEntity toEntity(){
        return MemberFileEntity.builder()
                .mfno(this.mfno)
                .mfname(this.mfname)
                .build();
    }
}
