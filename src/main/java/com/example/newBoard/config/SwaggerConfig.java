package com.example.newBoard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 서버 주소 표시 (Docker 환경이면 8080 기준)
                .servers(List.of(new Server().url("http://localhost:8080").description("Local Server")))
                .info(new Info()
                        .title("NewBoard REST API")
                        .description("""
                                💬 Spring Boot + MySQL + Kafka + Redis 통합 게시판 프로젝트  
                                - Article CRUD  
                                - Kafka Message Logging  
                                - Redis Cache  
                                - JWT (예정)
                                """)
                        .version("1.0.0")
                );
    }
}
