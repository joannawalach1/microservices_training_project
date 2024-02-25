package com.example.auth.fasada;

import com.example.auth.entity.AuthResponse;
import com.example.auth.entity.Code;
import com.example.auth.entity.User;
import com.example.auth.entity.dto.UserRegisterDto;
import com.example.auth.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<AuthResponse> addNewUser(@RequestBody UserRegisterDto userRegisterDto) {
        User register = userService.register(userRegisterDto);
        return ResponseEntity.ok(new AuthResponse(Code.SUCCESS));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user, HttpServletResponse response) {
        ResponseEntity<?> loginResponse = userService.login(user, response);

        if (loginResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(new AuthResponse(Code.SUCCESS));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(Code.LOGIN_FAILED));
        }
    }

}