package com.project.gateway.config;

import com.project.gateway.filter.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .cors().and()
            .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers("/auth/**").permitAll()

                .pathMatchers("/product/user/**").hasAuthority("USER")
                .pathMatchers("/product/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/product/**").hasAnyAuthority("USER", "ADMIN")

                .pathMatchers("/category/user/**").hasAuthority("USER")
                .pathMatchers("/category/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/category/**").hasAnyAuthority("USER", "ADMIN")

                .pathMatchers("/cart/user/**").hasAuthority("USER")
                .pathMatchers("/cart/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/cart/**").hasAnyAuthority("USER", "ADMIN")

                .pathMatchers("/orderbill/user/**").hasAuthority("USER")
                .pathMatchers("/orderbill/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/orderbill/**").hasAnyAuthority("USER", "ADMIN")

                .pathMatchers("/customers/user/**").hasAuthority("USER")
                .pathMatchers("/customers/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/customers/**").hasAnyAuthority("USER", "ADMIN")

                .pathMatchers("/items/user/**").hasAuthority("USER")
                .pathMatchers("/items/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/items/**").hasAnyAuthority("USER", "ADMIN")

                .pathMatchers("/orders/user/**").hasAuthority("USER")
                .pathMatchers("/orders/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/orders/**").hasAnyAuthority("USER", "ADMIN")

                .pathMatchers("/payment/user/**").hasAuthority("USER")
                .pathMatchers("/payment/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/payment/**").hasAnyAuthority("USER", "ADMIN")

                .anyExchange().authenticated()
            .and()
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }
}
