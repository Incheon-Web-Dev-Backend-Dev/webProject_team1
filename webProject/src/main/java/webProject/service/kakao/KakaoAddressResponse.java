package webProject.service.kakao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoAddressResponse {
    private List<Document> documents;

    @Getter
    @Setter
    public static class Document {
        private String address_name;  // 주소
        private String x;  // 경도
        private String y;  // 위도
    }
}
