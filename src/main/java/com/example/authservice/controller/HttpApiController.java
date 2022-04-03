package com.example.authservice.controller;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.dto.JoinDto;
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
    public String join(@RequestBody JoinDto joinDto){
        accountService.join(joinDto);

        return "회원가입완료";
    }

    // user,manager,admin 접근가능
    @GetMapping("/api/v1/user")
    public String user(){
        return "user";
    }

    // manager,admin 접근가능
    @GetMapping("/api/v1/manager")
    public String manager(){
        return "manager";
    }

    // admin 만 접근가능
   @GetMapping("/api/v1/admin")
    public String admin(){
        return "admin";
    }

}
