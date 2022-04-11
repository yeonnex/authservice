package com.example.authservice.service;

import com.example.authservice.Repository.AccountRepository;
import com.example.authservice.request.JoinRequest;
import com.example.authservice.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepo;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입
     */
    public Long join(JoinRequest request) throws IllegalAccessException {
        validateDuplicateMember(request); // 중복회원 검증
        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(encoder.encode(request.getPassword()));
        account.setNickname(request.getNickname());
        account.setRole("USER");

        return accountRepo.save(account).getId();
    }

    /**
     * 중복회원 검사
     */
    public void validateDuplicateMember(JoinRequest joinDto) throws IllegalAccessException {
        Account account = accountRepo.findByEmail(joinDto.getEmail());
        if(account != null){
            throw new IllegalAccessException("이미 존재하는 회원입니다");
        }
    }
}
