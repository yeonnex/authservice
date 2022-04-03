package com.example.authservice.service;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.dto.JoinDto;
import com.example.authservice.entity.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepo;
    private final BCryptPasswordEncoder encoder;


    public void join(JoinDto joinDto) {
        Account account = new Account();
        account.setEmail(joinDto.getEmail());
        account.setPassword(encoder.encode(joinDto.getPassword()));
        account.setName(joinDto.getName());
        account.setRole("USER");

        accountRepo.save(account);
    }
}
