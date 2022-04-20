package com.example.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id
    private Long id;
    private String refreshToken;
}
