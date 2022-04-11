package com.example.authservice.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JoinRequest {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
