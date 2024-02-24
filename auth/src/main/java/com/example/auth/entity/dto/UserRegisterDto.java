package com.example.auth.entity.dto;


import com.example.auth.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterDto {

    private String login;
    private String email;
    private String password;
    private Role role;
}
