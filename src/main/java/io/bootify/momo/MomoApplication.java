package io.bootify.momo;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {
        "io.bootify.momo.domain"
})
public class MomoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MomoApplication.class, args);
    }

    @PostConstruct
    public void checkEnv() {
        System.out.println("UPLOAD_DIR_PATH = " + System.getenv("UPLOAD_DIR_PATH"));
    }
}

