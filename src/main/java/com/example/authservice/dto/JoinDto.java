package com.example.authservice.dto;

import lombok.Data;

@Data
public class JoinDto {
    private String email;
    private String name;
    private String password;
}
