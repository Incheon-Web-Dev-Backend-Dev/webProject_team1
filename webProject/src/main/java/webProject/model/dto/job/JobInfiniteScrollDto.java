package webProject.model.dto.job;

import lombok.*;
import java.util.List;

@Getter @Setter @ToString @Builder @AllArgsConstructor @NoArgsConstructor
public class JobInfiniteScrollDto {
    private List<JobOfferDto> jobList;      // 구인글 데이터 리스트
    private boolean hasMore;                // 더 불러올 데이터가 있는지 여부
    private int lastId;                     // 마지막으로 불러온 글의 ID (다음 요청 시 사용)
}