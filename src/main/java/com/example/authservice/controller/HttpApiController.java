package com.example.authservice.controller;

import com.example.authservice.request.JoinRequest;
import com.example.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HttpApiController {

    private final AccountService accountService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseEntity<Map<String, Long>> join(@RequestBody JoinRequest joinRequest) throws IllegalAccessException {
        Long savedId = accountService.join(joinRequest);
        return new ResponseEntity<>(Map.of("userId", savedId),HttpStatus.OK);
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
