package webProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {
    public static void main(String[] args) {
        System.out.println("");
        SpringApplication.run(AppStart.class);
        System.out.println("hi");
    }
}
