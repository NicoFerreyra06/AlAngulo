package com.nicoferreyra.alangulo.config;

import com.nicoferreyra.alangulo.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. APAGAMOS CSRF (Esto es lo que te falta)
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login**", "/error", "/api/me").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/court/**", "/complex").permitAll()
                        .requestMatchers(HttpMethod.POST, "/court/**", "/complex/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/booking/**").hasAnyRole("USER", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/review/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("http://localhost:8080/api/me", true) // Redirige a /api/me al entrar
                )
                .build();
    }
}