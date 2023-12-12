package com.example.eventticketmanagement.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin.loginPage("/login").permitAll()
                        .successForwardUrl("/api/"))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/").authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("/login");
                }))
                .logout(logout -> logout.logoutUrl("/logout"))
                .build();
    }
}
