package com.example.authservice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    @Email
    private String email;
    private String password;
    private String name;
    private String role;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
