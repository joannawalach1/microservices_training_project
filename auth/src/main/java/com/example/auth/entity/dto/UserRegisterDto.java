package com.example.auth.entity.dto;


import com.example.auth.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class UserRegisterDto {
    @Length(min = 5, max = 50, message = "Login should have min 5 letters")
    private String login;
    @Email
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Length(min = 5, max = 50, message = "Password should have min 5 letters")
    private String password;
    private Role role;
}
