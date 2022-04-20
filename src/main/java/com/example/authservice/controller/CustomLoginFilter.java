package com.example.authservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authservice.auth.CustomUserDetails;
import com.example.authservice.entity.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 스프링 시큐리티에는 UsernamePasswordAuthenticationFilter 가 있음
 * /login 요청해서 username, password 전송하면 (post)
 * UsernamePasswordAuthenticationFilter 가 동작!
 *
 * 폼로그인.disable 해서 /login 이 무효화 되었기에 이 필터를 직접 등록해주는 것임.
*/
@RequiredArgsConstructor
@Data
@Slf4j
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청시, 로그인을 위해 실행되는 함수

    /**
     *
     * 1. authenticationManager 가 로그인시도
     * 2. CustomUserDetailsService가 호출되고, loadUserByUsername() 함수 실행됨.
     * 3. principal 을 세션에 담고(권한 관리를 위해서) 권한관리 필요없다면 세션 안만들어도 됨
     * 4. JWT 토큰을 만들어 응답해주면 됨.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{

            /**
             * 로그인시 유저가 보낸 데이터를 까보자!
             */
            ObjectMapper mapper = new ObjectMapper();// json 데이터 파싱해서 모델로 변환
            Account account = mapper.readValue(request.getInputStream(), Account.class);
            System.out.println(account);

            /**
             * 토큰으로 만들어줘야된다. 폼로그인때는 스프링이 알아서 해줬지만, 커스텀하게 로그인할 경우 직접 해줘야함
             */
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword());
            /**
             * 토큰을 authenticationManger 에게 던진다
             * authenticationManager 로 로그인 시도를 하면, CustomUserDetailsService 의 loadUserByUsername 이 호출된다.
             */
            log.info("로그인 시도중...");
            Authentication authentication = authenticationManager.authenticate(token);
            /**
             * 로그인이 잘 되었다면, authentication 에 유저 정보가 저장됨 (session 영역에 저장을 위해 리턴)
             * 리턴의 이유는 권한 관리를 시큐리티가 대신 해주기 때문에 편하려고 하는거
             *
             * 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리때문에 세션에 넣어줌
             *
             * 이제 JWT 토큰을 만들어줘야되는데, 이 함수말고, 이 함수가 실행되고 난 다음에 호출되는 함수에서 만들어주자!
             * */
            CustomUserDetails principal = (CustomUserDetails)authentication.getPrincipal(); // <- Object 를 리턴하기 때문에 캐스팅필요
            log.info("로그인되었습니다. 현재 로그인한 유저는: "+ principal.getUsername());

            return authentication; // authentication 을 리턴! 얘가 세션에 저장됨.
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 위의 attemptAuthentication 함수가 실행되고 난 다음에 호출되는 함수이다.
     * 여기서 JWT 토큰을 만들어주자~
     * 이걸 request 요청한 사용자에게 response 해주면 됨.
     */

    // RSA 방식은 아니고 Hash 암호 방식
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication 함수 실행");
        CustomUserDetails principal = (CustomUserDetails)authResult.getPrincipal();

        // 🌙 access token 만료시간은 10분 🌙
        String accessToken = JWT.create()
                .withClaim("id", principal.getAccount().getId())
                .withClaim("name" , principal.getUsername())
                .withClaim("email", principal.getAccount().getEmail())
                .withClaim("exp", System.currentTimeMillis() + 1000*60*10)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*10))
                        .sign(Algorithm.HMAC512("secret-lol-lol"));

        // 🌝 refresh token 만료시간은 1시간 🌝
        String refreshToken = JWT.create()
                .withClaim("id", principal.getAccount().getId())
                .withClaim("name", principal.getUsername())
                .withClaim("email", principal.getAccount().getEmail())
                .withClaim("exp", System.currentTimeMillis() + 1000*60*60)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60))
                .sign(Algorithm.HMAC512("secret-lol-lol"));




        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        // 🍪🍪 응답시 쿠키에 토큰 정보 저장하여 응답 🍪🍪
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }
}
