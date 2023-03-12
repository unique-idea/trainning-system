package com.fptacademy.training.config;

import com.fptacademy.training.security.Permissions;
import com.fptacademy.training.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private static final String[] AUTH_WHITELIST = {
            // swagger
            "/swagger-ui/**",
            "/v3/api-docs/**",
            // authenticate
            "/api/auth/login",
            "/api/auth/refresh"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .mvcMatchers(AUTH_WHITELIST).permitAll()
                .mvcMatchers(HttpMethod.DELETE, "/api/programs").hasAnyAuthority(Permissions.PROGRAM_MODIFY, Permissions.PROGRAM_FULL_ACCESS)
                .mvcMatchers(HttpMethod.PATCH, "/api/programs").hasAnyAuthority(Permissions.PROGRAM_MODIFY, Permissions.PROGRAM_FULL_ACCESS)
                .mvcMatchers(HttpMethod.POST, "/api/programs").hasAnyAuthority(Permissions.PROGRAM_CREATE, Permissions.PROGRAM_MODIFY, Permissions.PROGRAM_FULL_ACCESS)
                .mvcMatchers("/api/programs/**").hasAnyAuthority(Permissions.PROGRAM_VIEW, Permissions.PROGRAM_CREATE, Permissions.PROGRAM_MODIFY, Permissions.PROGRAM_FULL_ACCESS)
                .anyRequest().authenticated()
                .and().build();
    }
}
