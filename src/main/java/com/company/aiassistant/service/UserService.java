package com.company.aiassistant.service;


import com.company.aiassistant.entity.User;
import com.company.aiassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // =========================
    // CREATE USER
    // =========================
    public User createUser(
            String name,
            String email,
            String password
    ) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .dailyLimit(20)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    // =========================
    // FIND BY EMAIL
    // =========================
    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable"));
    }

    // =========================
    // FIND BY ID
    // =========================
    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable"));
    }

}
