package com.example.authservice;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountRunner implements ApplicationRunner {
    private final AccountRepository accountRepo;
    private final BCryptPasswordEncoder encoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setRoles("USER,ADMIN");
        account.setEmail("yeonnex@gmail.com");
        account.setName("seoyeon Jang");
        account.setPassword(encoder.encode("1234"));

        accountRepo.save(account);
    }
}
