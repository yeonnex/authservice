package com.example.authservice.filter;

import javax.servlet.*;
import java.io.IOException;

public class BarFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("BarFilter1 Bar Bar");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
