package com.example.authservice.jwt;


// 이걸로 반영 예정 !!
public interface JwtProperties {
    String SECRET = "jwt-lol-lol";
    int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
