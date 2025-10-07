package com.example.newBoard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ CSRF 보호 비활성화 (POST/PUT 요청 가능)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // ✅ /api/**는 인증 없이 허용
                        .anyRequest().permitAll() // ✅ 나머지도 허용 (지금은 모두 열기)
                );
        return http.build();
    }
}
