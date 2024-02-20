package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }
}
