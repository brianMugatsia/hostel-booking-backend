package com.hostel.hostel_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity // enables @PreAuthorize in controllers
@SpringBootApplication
public class HostelServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HostelServiceApplication.class, args);
    }
}
