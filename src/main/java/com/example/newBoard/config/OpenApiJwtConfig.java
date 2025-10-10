// src/main/java/com/example/newBoard/config/OpenApiJwtConfig.java
package com.example.newBoard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiJwtConfig {

    @Bean
    public OpenAPI openAPI() {
        final String schemeName = "bearerAuth";
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(
                        schemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                // 기본으로 모든 API에 JWT 요구(읽기 전용 엔드포인트는 컨트롤러/시큐리티에서 여전히 permit 가능)
                .addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}
