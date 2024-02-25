package com.example.auth.service;

import com.example.auth.entity.AuthResponse;
import com.example.auth.entity.Code;
import com.example.auth.entity.User;
import com.example.auth.entity.dto.UserRegisterDto;
import com.example.auth.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookiService cookiService;

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;



    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CookiService cookiService, JWTService jwtService, AuthenticationManager authenticateManager, @Value("${jwt.exp}") int exp, @Value("${jwt.refresh.exp}") int refreshExp) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cookiService = cookiService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticateManager;
        this.exp = exp;
        this.refreshExp = refreshExp;
    }
    private int exp;

    private int refreshExp;

    private User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.saveAndFlush(user);
    }

    public String generateToken(String userName, int refreshExp) {
        return jwtService.generateToken(userName, refreshExp);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public User register(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setLogin(userRegisterDto.getLogin());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        if (userRegisterDto.getRole() != null)
            user.setRole(userRegisterDto.getRole());
        return saveUser(user);
    }

    public ResponseEntity<?> login(User user, HttpServletResponse response) {
        user = userRepository.findUserByLogin(user.getUsername()).orElse(null);
        if (user != null) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                Cookie refresh = cookiService.generateCookie("refresh", generateToken(user.getUsername(),refreshExp), refreshExp);
                Cookie cookie = cookiService.generateCookie("Authorization", generateToken(user.getUsername(),exp), exp);
                response.addCookie(cookie);
                response.addCookie(refresh);
                return ResponseEntity.ok(
                        UserRegisterDto
                                .builder()
                                .login(user.getUsername())
                                .email(user.getEmail())
                                .build());
            } else {
                ResponseEntity.ok(new AuthResponse(Code.A1));
            }
        }
        return ResponseEntity.ok(new AuthResponse(Code.A2));
    }
}
