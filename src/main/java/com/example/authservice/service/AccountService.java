package com.example.authservice.service;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.entity.Account;
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

    public List<String> getAccountRoles(Account account){
        return List.of(account.getRoles().split(","));
    }

    public void join(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        account.setRoles("ROLE_USER");
        accountRepo.save(account);
    }
}
