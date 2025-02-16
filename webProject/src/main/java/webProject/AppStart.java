package webProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
//@EnableJpaRepositories(basePackages = "webProject.model.repository")
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
        System.out.println("Server Start");
    }
}
