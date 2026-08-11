package com.jlim.store.orderservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MEAT: this is only for browser-based clients (swagger) to make calls to api gateway -> catalog-service
 *
 * */
@Configuration
class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowCredentials(false);
    }
}

