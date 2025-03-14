package webProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // (Spring 설정 클래스)애플래이케이션 구성에 적용
public class WebConfig implements WebMvcConfigurer { // MVC 인터페이스 구현
    // addResourceHandlers : js/img 등 정적 리소스 매핑 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //  registry.addResourceHandler() : 웹 요청 경로 패턴 설정, img/review 하위 모든 경로를 포함한다.
        registry.addResourceHandler("/img/review/**")
                .addResourceLocations("file:/Users/jimyung/webProject_team1/webProject/build/resources/main/static/img/review/");
    }
}
