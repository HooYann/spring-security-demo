package cn.beautybase.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.beautybase.authorization"})
public class CenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class);
    }
}
