package webProject.model.dto.job;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPageDto {
    private int page;
    private int totalpage;
    private long totalcount;
    private int startbtn;
    private int endbtn;

    // Object 는 자바의 최상위 클래스 이므로 모든 타입들의 자료들을 저장할 수 있다.
    // 즉 data 에는 List<BoardDto> 혹은 List<ReplyDto> 다양한 페이징한 정보를 대입하기 위해서 Object 를 씀
    private Object data;
}

