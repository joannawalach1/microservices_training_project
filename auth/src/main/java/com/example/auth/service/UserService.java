package com.example.auth.service;

import com.example.auth.entity.AuthResponse;
import com.example.auth.entity.Code;
import com.example.auth.entity.User;
import com.example.auth.entity.dto.UserRegisterDto;
import com.example.auth.exceptions.ExistingUserWithEmail;
import com.example.auth.exceptions.ExistingUserWithName;
import com.example.auth.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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
    private final int exp;

    private final int refreshExp;

    private User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public String generateToken(String userName, int refreshExp) {
        return jwtService.generateToken(userName, refreshExp);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public User register(UserRegisterDto userRegisterDto) throws ExistingUserWithName {
        userRepository.findUserByLogin(userRegisterDto.getLogin()).ifPresent(value -> {
                    throw new ExistingUserWithName("Użytkownik o tym loginie już istnieje");
                });
        userRepository.findUserByEmail(userRegisterDto.getLogin()).ifPresent(value -> {
            throw new ExistingUserWithEmail("Użytkownik o tym emailu już istnieje");
        });
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
                                .role(user.getRole())
                                .build());
            } else {
                ResponseEntity.ok(new AuthResponse(Code.A1));
            }
        }
        return ResponseEntity.ok(new AuthResponse(Code.A2));
    }
    public void validateToken(HttpServletRequest request,HttpServletResponse response) throws ExpiredJwtException, IllegalArgumentException {
        String token = null;
        String refresh = null;
        if (request.getCookies() != null) {
            for (Cookie value : Arrays.stream(request.getCookies()).toList()) {
                if (value.getName().equals("Authorization")) {
                    token = value.getValue();
                } else if (value.getName().equals("refresh")) {
                    refresh = value.getValue();
                }
            }
        } else {
            log.info("Can't login because in token is empty");
            throw new IllegalArgumentException("Token can't be null");
        }
        try {
            jwtService.validateToken(token);
        } catch (IllegalArgumentException | ExpiredJwtException e) {
            jwtService.validateToken(refresh);
            Cookie refreshCokkie = cookiService.generateCookie("refresh", jwtService.refreshToken(refresh, refreshExp), refreshExp);
            Cookie cookie = cookiService.generateCookie("Authorization", jwtService.refreshToken(refresh, exp), exp);
            response.addCookie(cookie);
            response.addCookie(refreshCokkie);
        }
    }}
