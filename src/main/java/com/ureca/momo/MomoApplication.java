package com.ureca.momo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.ureca.momo.domain")  // 엔티티 위치 지정
@EnableJpaRepositories(basePackages = "com.ureca.momo.domain")  // 리포지토리 위치 지정
public class MomoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomoApplication.class, args);
	}

}
