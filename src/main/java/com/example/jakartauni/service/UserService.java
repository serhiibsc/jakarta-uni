package com.example.jakartauni.service;

import com.example.jakartauni.security.Encryptor;
import com.example.jakartauni.entity.User;
import com.example.jakartauni.repository.UserRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.Optional;

@Stateless
public class UserService {
    @EJB
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void createDefaultAdmin() {
        User admin = User.builder()
                .id(1L)
                .login("admin")
                .password(Encryptor.encryptSha256("admin"))
                .role(User.UserRole.ADMIN)
                .build();
        userRepository.create(admin);
    }
}
