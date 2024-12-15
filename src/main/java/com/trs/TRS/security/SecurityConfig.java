package com.trs.TRS.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF (if needed)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/user/auth/login", "/user/auth/signup").permitAll() // Open endpoints
                                .anyRequest().authenticated() // Secure all other endpoints
                )
                .httpBasic(withDefaults()); // Use HTTP Basic Authentication (can replace with JWT filter in future)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // For encrypting passwords
    }
}

