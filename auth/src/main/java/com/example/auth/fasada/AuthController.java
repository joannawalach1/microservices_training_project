package com.example.auth.fasada;

import com.example.auth.entity.User;
import com.example.auth.entity.dto.UserRegisterDto;
import com.example.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/addUser")
    public ResponseEntity<User> addNewUser(@RequestBody UserRegisterDto userRegisterDto) {
        User register = userService.register(userRegisterDto);
        return ResponseEntity.status(HttpStatus.OK).body(register);

    }
}
