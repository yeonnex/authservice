package com.example.authservice.config;

import com.example.authservice.filter.CustomLoginFilter;
import com.example.authservice.filter.FooFilter1;
import com.example.authservice.filter.FooFilter2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter { // <- 시큐리티 필터!

    private final CorsFilter corsFilter; // 앞서 빈으로 등록하였었음. 주입받자. 스프링의 CorsFilter 를 커스텀하게 설정한 필터. "모든 요청"은 이 필터를 탄다.

    // Authentication (권한 관련)
    @Override // 이 서버는 stateless 서버이며, 크로스 오리진 요청을 허용한다. 즉 모든 요청을 허용한다. 폼 로그인도 쓰지 않음.
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // "이 서버는 stateless 서버야!!!"세션을 만들지 않겠다는 뜻. STATELESS 서버
                .and()
//                .addFilterBefore(new FooFilter1(), BasicAuthenticationFilter.class) // 시큐리티의 필터가 일반 필터보다 "먼저" 동작한다
                .addFilter(corsFilter) // 이 필터가 잘 동작하는지의 여부는 자바스크립트로 여기로 요청을 보내보면 알 수 있다. @CrossOrigin (인증없어도 되는데서만 동작. 인증이 필요한 경로 요청시 먹지 않음. 인증이 필요한 크로스 오리진 요청시에는 반드시 "시큐리티필터"에 크로스 오리진 설정을 해주어야 한다.
                .addFilter(new CustomLoginFilter(authenticationManager()))
                .formLogin().disable() // 폼 로그인 안씀
                .httpBasic().disable() // httpBasic 말고 Bearer 쓸거임
                // 로그인을 진행하는 필터이기 때문에, 로그인을 진행해주는 authenticationManager 를 인자로 넘겨야 한다.
                .authorizeRequests() // 권한이 있어야 하는 요청들
                /**
                 * USER 나 ADMIN 등 권한 없이 그냥 접근시 403상태코드(Forbidden, 권한없음) 을 내려줌!
                 */
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")

                .anyRequest().permitAll(); // 그 외의 다른 요청은 전부 권한 없이 들어갈 수 있다!
        /**
         * 기본적인 http 로그인방식으로 쓰지 않으며,
         * 세션도 만들지 않는다.
         *
         * jwt 서버니까~
         */
    }
}
