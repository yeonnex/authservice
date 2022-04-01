package com.example.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Cross Origin 간 요청허용 위해 만든 필터입니다.
 */
// 이렇게 달랑 있어서는 의미가 없음. "시큐리티 필터에 등록" 해주어야 함
@Configuration
public class CorsConfig{

    // 스프링 web 에 있는 CorsFilter 임.
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답을 할 때 json 을 자바스크립트에서 처리할 수 있게함
        config.addAllowedOrigin("*"); // 모든 ip 에 응답을 허용하겠다
        config.addAllowedHeader("*"); // 모든 header 에 응답을 허용하겠다
        config.addAllowedMethod("*"); // 모든 메서드(GET, POST, ...) 에 응답을 허용하겠다
        source.registerCorsConfiguration("/api/**", config); // /api/** 로 들어오는 모든 주소는 이 config 를 따르라

        return new CorsFilter(source);
    }
}
