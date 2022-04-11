package com.example.authservice;

import com.example.authservice.request.JoinRequest;
import com.example.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountRunner implements ApplicationRunner {
    private final AccountService accountService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JoinRequest request = new JoinRequest();
        request.setEmail("yeonnex@gmail.com");
        request.setNickname("seoyeon");
        request.setPassword("1234");
        accountService.join(request);
    }
}
