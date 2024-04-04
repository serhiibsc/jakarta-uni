package com.example.jakartauni.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Builder
@RequiredArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Enumerated(EnumType.STRING)

    private UserRole role;
    private String name;
    @Column(unique = true)
    private String login;
    public enum UserRole {
        ADMIN, GUEST;
        public String getName() {
            return name().toLowerCase();
        }
    }
}