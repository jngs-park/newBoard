package com.example.newBoard.config;

import com.example.newBoard.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectProvider<JwtAuthenticationFilter> jwtFilterProvider;
    private final boolean jwtEnabled;

    public SecurityConfig(ObjectProvider<JwtAuthenticationFilter> jwtFilterProvider,
                          @Value("${app.jwt.enabled:false}") boolean jwtEnabled) {
        this.jwtFilterProvider = jwtFilterProvider;
        this.jwtEnabled = jwtEnabled;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> {
            // Swagger & 인증 API는 항상 허용
            auth.requestMatchers(
                    "/",
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/webjars/**",
                    "/actuator/health"
            ).permitAll();

            if (jwtEnabled) {
                // JWT가 켜져있을 때만 보호
                auth.requestMatchers(HttpMethod.POST,   "/api/articles/**").authenticated();
                auth.requestMatchers(HttpMethod.PUT,    "/api/articles/**").authenticated();
                auth.requestMatchers(HttpMethod.DELETE, "/api/articles/**").authenticated();
                auth.anyRequest().permitAll();
            } else {
                // 기본은 모든 요청 허용 (기존 동작 유지)
                auth.anyRequest().permitAll();
            }
        });

        if (jwtEnabled) {
            JwtAuthenticationFilter jwtFilter = jwtFilterProvider.getIfAvailable();
            if (jwtFilter != null) {
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            }
        }

        return http.build();
    }
}
