package com.example.jakartauni;

import com.example.jakartauni.user.User;
import com.example.jakartauni.user.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.Objects;

@Singleton
@Startup
public class ApplicationInitializer {

    @EJB
    private UserService userService;

    @PostConstruct
    public void onStartup() {
        User user = userService.findUserByUsername("jaka-admin");
        if (Objects.isNull(user)) {
            userService.createDefaultAdmin();
        }
    }
}