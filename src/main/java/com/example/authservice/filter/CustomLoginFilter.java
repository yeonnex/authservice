package com.example.authservice.filter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 스프링 시큐리티에는 UsernamePasswordAuthenticationFilter 가 있음
 * /login 요청해서 username, password 전송하면 (post)
 * UsernamePasswordAuthenticationFilter 가 동작!
 *
 * 폼로그인.disable 해서 /login 이 무효화 되었기에 이 필터를 직접 등록해주는 것임.
*/
@RequiredArgsConstructor
@Data
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청시, 로그인을 위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
        // authenticationManager 로 로그인 시도를 하면, CustomUserDetailsService 의 loadUserByUsername 이 호출된다.
    }
}
