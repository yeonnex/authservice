package com.example.authservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.auth.CustomUserDetails;
import com.example.authservice.entity.Account;
import com.example.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 시큐리티는 많은 필터들을 가지고 있는데, 그 필터들 중 BasicAuthenticationFilter 라는 필터가 있음.
 * 권한이나 인증이 필요한 주소를 요청했을 때 이 필터를 "무조건" 카게 되어있음
 * 만약에 권한이나 인증이 필요하지 않다면 이 필터를 타지 않음.
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private AccountRepository accountRepo;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountRepository userRepository) {
        super(authenticationManager);
        this.accountRepo = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("JwtAuthorizationFilter 의 doFilterInternal 호출");
        String header = request.getHeader("Authorization");
        System.out.println("헤더출력: " + header);
        if(header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        System.out.println("header : "+header);
        String token = request.getHeader("Authorization")
                .replace("Bearer ", "");

        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
        String email = JWT.require(Algorithm.HMAC512("secret-lol-lol")).build().verify(token).getClaim("email").asString();

        if(email != null) {
            Account account = accountRepo.findByEmail(email);

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            CustomUserDetails principalDetails = new CustomUserDetails(account);
            System.out.println("권한 출력: " + principalDetails.getAuthorities());
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                            null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                            principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}