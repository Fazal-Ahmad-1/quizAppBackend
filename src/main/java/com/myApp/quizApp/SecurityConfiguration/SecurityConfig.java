package com.myApp.quizApp.SecurityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for Postman testing (for JSON APIs)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // allow these without authentication
                        .requestMatchers("/user/**","/quiz/submit/**","/quiz/**","/question/**").permitAll()
                        // every other request must be authenticated
                        .anyRequest().authenticated()
                )
                // You can keep httpBasic for now, or customize JWT, etc.
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
