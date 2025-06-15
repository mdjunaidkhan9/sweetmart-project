package com.project.auth.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a Spring configuration class (like XML config but Java-based)
public class SecurityConfig {

    @Bean // Exposes this method's return value (PasswordEncoder) as a Spring-managed bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encrypts passwords using BCrypt hashing
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disables CSRF protection (needed for APIs and POST testing)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allows all requests (no restrictions)
            );
        return http.build(); // Builds and returns the security filter chain
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Provides AuthenticationManager bean (used for login)
    }
}

