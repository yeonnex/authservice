package com.example.authservice.Repository;

import com.example.authservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token); // 토큰 존재 여부 판단
}
