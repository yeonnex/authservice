package com.example.authservice.myexpectedreturn;


import com.example.authservice.entity.Account;
import com.example.authservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpectedReturnTest {

    @MockBean
    AccountService accountService;

    @Test
    public void 권한_리스트_반환_테스트(){
        Account account = new Account();
        account.setRoles("USER,ADMIN");

        assertEquals(2,(accountService.getAccountRoles(account)).size());

    }
}
