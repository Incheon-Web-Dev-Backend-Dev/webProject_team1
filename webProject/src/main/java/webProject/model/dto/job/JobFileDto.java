package webProject.model.dto.job;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import webProject.model.entity.job.JobFileEntity;

import java.util.List;

@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class JobFileDto {

    private int jfno; // 구인글의 첨부파일 번호 PK
    private String jfname; // 구인글의 첨부파일 이름

    private int jono; // 구인글 번호 FK

    public JobFileEntity toEntity(){
        return JobFileEntity.builder()
                .jfno(this.jfno).jfname(this.jfname).build();
    }
}
