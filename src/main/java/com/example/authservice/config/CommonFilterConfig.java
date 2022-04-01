package com.example.authservice.config;

import com.example.authservice.filter.BarFilter1;
import com.example.authservice.filter.FooFilter1;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonFilterConfig {
    @Bean
    FilterRegistrationBean<FooFilter1> fooFilter1(){
        FilterRegistrationBean<FooFilter1> fooBean = new FilterRegistrationBean<>(new FooFilter1());
        fooBean.addUrlPatterns("/foo/*"); // /foo/* 요청에 대해 Foo 필터 적용
        fooBean.setOrder(0); // 필터의 순서를 정할 수 있는데, 낮은 번호일수록 필터중에서 가장 먼저 실행된다.
        return fooBean;
    }

    @Bean
    FilterRegistrationBean<BarFilter1> barFilter1(){
        FilterRegistrationBean<BarFilter1> barBean = new FilterRegistrationBean<>(new BarFilter1());
        barBean.addUrlPatterns("/bar/*"); // /bar/* 요청에 대해 Bar 필터 적용
        barBean.setOrder(1); // 무조건 Foo 필터 이후에 실행됨
        return barBean;
    }
}
