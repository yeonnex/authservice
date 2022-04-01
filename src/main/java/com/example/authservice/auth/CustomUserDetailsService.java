package com.example.authservice.auth;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * http://localhost:8080/login 요청시
 * loadUserByUsername 함수가 호출된다.
 * DB에서 해당 Username (이 프로젝트의 경우 email) 이 있는지 찾아온다.
 *
 * /login 은 내가 따로 컨트롤러로 만들지 않았는데, 스프링이 그냥 낚아채감 <- 근데 이건 폼 로그인일떄의 소리.
 * 이 프로젝트는 폼 로그인을 disable 했으므로 따로 설정필요
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username + "DB 에서 찾는중...");
        Account account = accountRepo.findByEmail(username);
        if(account == null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(account);
    }
}
