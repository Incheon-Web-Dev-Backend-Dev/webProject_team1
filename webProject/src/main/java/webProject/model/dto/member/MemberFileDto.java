package webProject.model.dto.member;

import lombok.*;
import webProject.model.entity.member.MemberFileEntity;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberFileDto {
    private int mfno;
    private String mfname;
    private String profile;
    private String cdate;

    // 이미지 URL (웹에서 접근 가능한 경로)
    private String imageUrl;

    // Entity -> Dto 변환 메서드 (정적 팩토리 메서드)
    public static MemberFileDto fromEntity(MemberFileEntity entity) {
        return MemberFileDto.builder()
                .mfno(entity.getMfno())
                .mfname(entity.getMfname())
                .profile(entity.getProfilename())
                .cdate(entity.getCdate() != null ? entity.getCdate().toString() : null)
                .build();
    }

    // Dto -> Entity 변환 메서드
    public MemberFileEntity toEntity() {
        MemberFileEntity entity = new MemberFileEntity();
        entity.setMfno(this.mfno);
        entity.setMfname(this.mfname);
        entity.setProfilename(this.profile);
        return entity;
    }
}