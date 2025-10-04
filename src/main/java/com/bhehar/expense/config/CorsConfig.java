package com.bhehar.expense.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // @Value("${spring.cors.allowed.origin}")
    // private String allowedOrigin;

    @Value("${spring.cors.allowed.origins}")
  private String[] allowedOrigins;  // or List<String>

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] methods = new String[]{"GET", "PUT", "POST", "DELETE"};
        registry.addMapping("/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods(methods);
    }
}
