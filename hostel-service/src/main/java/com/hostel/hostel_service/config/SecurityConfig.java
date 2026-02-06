package com.hostel.hostel_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // enables @PreAuthorize in controllers
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // Admin-only endpoints
                        .requestMatchers("/api/hostels/**", "/api/photos/**").hasRole("ADMIN")
                        // Student + Admin + Owner can view
                        .requestMatchers("/api/view/**", "/api/public/**").hasAnyRole("STUDENT", "ADMIN", "OWNER")
                        // everything else requires authentication
                        .anyRequest().authenticated())
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
