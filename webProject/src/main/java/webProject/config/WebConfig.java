package webProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.base-path}")
    private String uploadBasePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기존 리소스 핸들러 유지
        registry.addResourceHandler("/img/review/**")
                .addResourceLocations("file:/Users/jimyung/webProject_team1/webProject/build/resources/main/static/img/review/");

        // 외부 업로드 폴더에 대한 리소스 핸들러 추가
        // 외부 폴더 확인 및 생성
        createDirectoryIfNotExists(uploadBasePath);
        createDirectoryIfNotExists(uploadBasePath + "/profile");
        createDirectoryIfNotExists(uploadBasePath + "/company");
        createDirectoryIfNotExists(uploadBasePath + "/master");

        // /uploads/** 경로 요청을 외부 폴더로 연결
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadBasePath + "/");

        System.out.println("Resource handler added: /uploads/** -> " + uploadBasePath);
    }

    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            System.out.println("Directory created: " + path + " - " + (created ? "Success" : "Failed"));
        }
    }
}