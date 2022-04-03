package com.example.authservice;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.dto.JoinDto;
import com.example.authservice.entity.Account;
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
        JoinDto joinDto = new JoinDto();
        joinDto.setEmail("yeonnex@gmail.com");
        joinDto.setName("seoyeon");
        joinDto.setPassword("1234");
        accountService.join(joinDto);
    }
}
