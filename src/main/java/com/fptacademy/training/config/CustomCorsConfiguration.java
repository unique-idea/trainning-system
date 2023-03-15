package com.fptacademy.training.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

public class CustomCorsConfiguration {
    public CustomCorsConfiguration(CorsConfigurer<HttpSecurity> c) {
        CorsConfigurationSource source = request -> {
            org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedOrigins(List.of("*"));
            config.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept", "responseType", "Authorization", "refresh_token"));
            config.setExposedHeaders(List.of("access_token", "refresh_token"));
            config.setAllowedMethods(List.of("GET", "OPTIONS", "POST", "PUT", "DELETE", "HEAD", "PATCH"));
            return config;
        };
        c.configurationSource(source);
    }
}