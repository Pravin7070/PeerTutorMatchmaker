package com.peertutor.matchmaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus; // 🌟 NEW IMPORT
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint; // 🌟 NEW IMPORT

// Removed static import of withDefaults to ensure we use the explicit configuration
// import static org.springframework.security.config.Customizer.withDefaults; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines the password encoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain (authentication rules).
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless REST APIs
            .authorizeHttpRequests(auth -> auth
                // Allow public access to registration
                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                
                // Note: /api/match/tutors and /api/subjects are public
                .requestMatchers(HttpMethod.GET, "/api/match/tutors").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/subjects").permitAll()

                // All other /api/** endpoints require authentication (including /api/auth/me)
                .requestMatchers("/api/**").authenticated() 
                
                // Any other request can be permitted
                .anyRequest().permitAll() 
            )
            // 🌟 FIX: Configure httpBasic to use a custom entry point.
            // This returns a simple 401 response without the 'WWW-Authenticate' header,
            // preventing the native browser popup and allowing JS to handle the failure.
            .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
            ));

        return http.build();
    }
}