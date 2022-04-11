package com.example.authservice.service;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.request.JoinRequest;
import com.example.authservice.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@RequiredArgsConstructor
class AccountServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void 회원가입() throws IllegalAccessException {
        // given
        JoinRequest request = new JoinRequest();
        request.setEmail("yeonnex@gmail.com");
        request.setNickname("seoyeon");
        request.setPassword("1234");

        accountService.join(request);

        // then
        Account findAccount = accountRepository.findByEmail("ddd@gmail.com");
        assertEquals(findAccount.getEmail(), request.getEmail());
    }

}