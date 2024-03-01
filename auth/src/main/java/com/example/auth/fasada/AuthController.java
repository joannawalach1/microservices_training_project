package com.example.auth.fasada;

import com.example.auth.entity.AuthResponse;
import com.example.auth.entity.Code;
import com.example.auth.entity.User;
import com.example.auth.entity.ValidationMessage;
import com.example.auth.entity.dto.UserRegisterDto;
import com.example.auth.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<AuthResponse> addNewUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
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

    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(HttpServletRequest request) {
        try {
            userService.validateToken(String.valueOf(request));
            return ResponseEntity.ok(new AuthResponse(Code.PERMIT));
        }
        catch (IllegalArgumentException | ExpiredJwtException e) {
            return ResponseEntity.status(401).body(new AuthResponse(Code.A1));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationMessage handleValidationExceptions(
        MethodArgumentNotValidException ex
                ){
    return new ValidationMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
    }

