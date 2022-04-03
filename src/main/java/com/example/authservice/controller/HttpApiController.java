package com.example.authservice.controller;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.entity.Account;
import com.example.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HttpApiController {

    private final AccountService accountService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @PostMapping("/join")
    public String join(@RequestBody Account account){

        accountService.join(account);

        return "회원가입완료";
    }



}
