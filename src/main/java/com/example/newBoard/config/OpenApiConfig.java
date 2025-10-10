// src/main/java/com/example/newBoard/config/OpenApiConfig.java
package com.example.newBoard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "NewBoard REST API",
                version = "1.0.0",
                description = "Spring Boot + MySQL + Kafka + Redis + JWT"
        ),
        servers = { // 🔹 포트/호스트 자동 매칭 (8081로 접속하면 8081로 호출됨)
                @Server(url = "/", description = "Same origin")
        },
        security = { // 🔹 전역 보안 요구(개별 엔드포인트 permitAll이면 그대로 열림)
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {}
