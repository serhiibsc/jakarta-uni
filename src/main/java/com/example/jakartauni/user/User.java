package com.example.jakartauni.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class User {
    private Long id;

    private String password;
    private UserRole role;
    private String name;
    private String login;

    public enum UserRole {
        ADMIN, GUEST;

        public String getName() {
            return name().toLowerCase();
        }
    }
}
