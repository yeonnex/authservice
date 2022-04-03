package com.example.authservice.service;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.dto.JoinDto;
import com.example.authservice.entity.Account;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
    void 회원가입(){
        // given
        JoinDto joinDto = new JoinDto();
        joinDto.setEmail("yeonnex@gmail.com");
        joinDto.setName("seoyeon");
        joinDto.setPassword("1234");

        accountService.join(joinDto);

        // then
        Account findAccount = accountRepository.findByEmail("ddd@gmail.com");
        assertEquals(findAccount.getEmail(), joinDto.getEmail());
    }

}