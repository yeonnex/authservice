package com.example.authservice.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// 이렇게 만드는 것만으로는 의미가 없고, 필터에 "등록"해줘야함.
public class FooFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("FooFilter1 Foo Foo");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        // Authorization 으로 넘긴 값이 foofoo 가 아니면 컨트롤러에 진입조차 못하게 해보자.
        // foofoo 여야 그 다음 필터를 탈 수 있게
        // 이 필터는 당연히 시큐리티가 동작하기 "전에" 작동해야겠지?
        String headerAuth = req.getHeader("Authorization");
        if (headerAuth == null){
            PrintWriter out = res.getWriter();
            out.println("Authorization Fail! who are you?!"); // 더이상 필터타지 않음. 클라에게 응답으로 보냄
        }
        else if (headerAuth.equals("foofoo")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            PrintWriter out = res.getWriter();
            out.println("Authorization Fail! who are you?!"); // 더이상 필터타지 않음. 클라에게 응답으로 보냄
        }

    }
}
