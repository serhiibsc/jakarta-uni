package com.example.jakartauni.user;

import com.example.jakartauni.encrypt.Encryptor;
import jakarta.ejb.Stateless;

@Stateless
public class UserService {
    public User findUserByUsername(String username) {
        return User.builder()
                .id(1L)
                .login("jakarta")
                .password(Encryptor.encryptSha256("pass"))
                .role(User.UserRole.ADMIN)
                .build();
    }
}
