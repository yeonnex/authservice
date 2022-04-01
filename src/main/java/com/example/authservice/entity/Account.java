package com.example.authservice.entity;

import lombok.Data;
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
    private String roles; // USER, ADMIN

    @CreationTimestamp
    private LocalDateTime createdAt;

    public List<String> getRoles(){
        if(this.roles.length() > 0){
            return List.of(this.roles.split(","));
        }
        return new ArrayList<>(); // null 이 안뜨게 해주자.
    }
}
