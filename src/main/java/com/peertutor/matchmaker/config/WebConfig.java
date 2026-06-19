package com.peertutor.matchmaker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// DO NOT ADD @EnableWebMvc HERE - This is correct.
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Allow CORS for all /api/ endpoints
            
            // 🌟 FIX 1: Must use specific origins when allowCredentials is true
            // Replace 3000 with the actual port your frontend is running on.
            .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000") 
            
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            
            // 🌟 FIX 2: Must be TRUE to allow Basic Authentication headers (credentials)
            .allowCredentials(true);
    }
}