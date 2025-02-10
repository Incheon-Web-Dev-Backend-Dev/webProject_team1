package webProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AppStart {
    public static void main(String[] args) {
        System.out.println("");
        SpringApplication.run(AppStart.class);
        System.out.println("hi");
        System.out.println("bye");

        System.out.println("김진홍 테스트");
        System.out.println("김진홍 테스트2");
        System.out.println("김진홍 테스트3");
        System.out.println("선근호 테스트3");
        System.out.println("선근호 테스트3");
        System.out.println("선근호 테스트3");
    }
}
