package io.bootify.momo;

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
}

