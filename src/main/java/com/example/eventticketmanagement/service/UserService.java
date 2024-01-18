package com.example.eventticketmanagement.service;

import com.example.eventticketmanagement.entity.UserEntity;
import com.example.eventticketmanagement.exception.UserWithResourceNotFound;
import com.example.eventticketmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public UserEntity saveUser(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity findByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserWithResourceNotFound("user with username %s was not found".formatted(username));
        }

        return user.orElseThrow(
                () -> new UserWithResourceNotFound("user with username %s was not found".formatted(username))
        );
    }

}
