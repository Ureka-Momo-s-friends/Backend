package com.ureca.momo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Momo API Documentation")
                        .description("Momo 서비스의 API 문서입니다.")
                        .version("v1.0.0"))
                .addServersItem(new Server().url("/"));
    }
}