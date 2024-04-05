package com.example.jakartauni.user;

import com.example.jakartauni.security.Encryptor;
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
                .login("jaka-admin")
                .password(Encryptor.encryptSha256("pass"))
                .role(User.UserRole.ADMIN)
                .build();
        userRepository.create(admin);
    }
}
