package com.homehero.homehero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir origens do frontend
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",  // React/Vite
            "http://localhost:4200",  // Angular
            "http://localhost:5173"   // Vite padrão
        ));
        
        // Métodos HTTP permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Headers permitidos
        config.setAllowedHeaders(Arrays.asList("*"));
        
        // Permitir credenciais (cookies, auth headers)
        config.setAllowCredentials(true);
        
        // Tempo de cache para preflight requests
        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

