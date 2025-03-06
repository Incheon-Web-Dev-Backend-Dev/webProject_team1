package webProject.service.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoAddressService {

    // 카카오 API URL
    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    @Value("${kakao.api.key}")
    private String KAKAO_API_KEY;  // 여기에 실제 REST API 키를 넣으세요

    public double[] getCoordinatesFromAddress(String address) {
        // 주소에 대한 요청을 보낼 URL을 생성
        String url = KAKAO_API_URL + "?query=" + address;

        // RestTemplate을 이용해 HTTP 요청을 보냄
        RestTemplate restTemplate = new RestTemplate();

        // 헤더에 API key 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);  // 'Authorization'의 오타 수정
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<KakaoAddressResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoAddressResponse.class);

        KakaoAddressResponse addressResponse = response.getBody();
        if (addressResponse != null && !addressResponse.getDocuments().isEmpty()) {
            KakaoAddressResponse.Document document = addressResponse.getDocuments().get(0);

            // 위도 (Y 값)와 경도 (X 값) 가져오기
            double latitude = Double.parseDouble(document.getY());  // 위도는 Y 값
            double longitude = Double.parseDouble(document.getX()); // 경도는 X 값

            return new double[]{latitude, longitude};
        }
        return null;
    }
}
