package com.example.authservice;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.request.JoinRequest;
import com.example.authservice.entity.Account;
import com.example.authservice.request.LoginRequest;
import com.example.authservice.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
@AutoConfigureMockMvc
class AccountTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();


    @Test
    void 회원가입() throws IllegalAccessException {
        // given
        JoinRequest request = new JoinRequest();
        request.setEmail("hepi6639@gmail.com");
        request.setNickname("qedqed");
        request.setPassword("1234");

        accountService.join(request);

        // then
        Account findAccount = accountRepository.findByEmail("hepi6639@gmail.com");
        assertEquals(findAccount.getEmail(), request.getEmail());
    }

    @BeforeEach
    void 강제회원가입() throws IllegalAccessException {
        JoinRequest request = new JoinRequest();
        request.setEmail("yeonnex@gmail.com");
        request.setNickname("seoyeon");
        request.setPassword("1234");

        accountService.join(request);
    }

    @Test
    void 성공적_로그인시_액세스토큰과_리프레시토큰_반환() throws Exception {
        LoginRequest request = LoginRequest.builder().email("yeonnex@gmail.com").password("1234").build();
        Arrays.stream(mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getCookies()).forEach(cookie -> {
            System.out.println("🍪" + cookie.getName() + cookie.getValue());
        });

    }



}