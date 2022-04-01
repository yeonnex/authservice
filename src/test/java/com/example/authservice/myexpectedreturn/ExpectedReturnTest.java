package com.example.authservice.myexpectedreturn;


import com.example.authservice.entity.Account;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpectedReturnTest {
    @Test
    public void 권한_리스트_반환_테스트(){
        Account account = new Account();
        account.setRoles("USER,ADMIN");
        List<String> roles = account.getRoles();
        assertEquals(2, roles.size());
        assertEquals("USER", roles.get(0));
    }
}
