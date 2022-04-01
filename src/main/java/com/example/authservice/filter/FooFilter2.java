package com.example.authservice.filter;


import javax.servlet.*;
import java.io.IOException;

// 이렇게 만드는 것만으로는 의미가 없고, 필터에 "등록"해줘야함.
public class FooFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("FooFilter2 Foo Foo");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
