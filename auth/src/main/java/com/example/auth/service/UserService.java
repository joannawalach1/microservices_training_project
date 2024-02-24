package com.example.auth.service;

import com.example.auth.entity.User;
import com.example.auth.entity.dto.UserRegisterDto;
import com.example.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    private User saveUser(User user) {
        passwordEncoder.encode(user.getPassword());
        return userRepository.saveAndFlush(user);
    }

    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
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
}
