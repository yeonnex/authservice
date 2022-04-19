package com.example.authservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthserviceApplicationTests {

    @Value("${jwt.secret}")
    String secretKey;

    @Test
    void contextLoads() {

    }

    @Test
    void 토큰_잘읽어오나(){
        System.out.println("시크릿키: " + secretKey);
    }

}
