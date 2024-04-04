package com.example.jakartauni.filter;

import com.example.jakartauni.encrypt.Encryptor;
import com.example.jakartauni.user.User;
import com.example.jakartauni.user.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter("/api/*")
public class AuthorizationFilter implements Filter {
    @EJB
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Credentials are empty");
            return;
        }
        if (!login.matches("[a-zA-Z0-9\\-]{3,10}")) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Login is suspicious");
            return;
        }

        User user = userService.findByUsername(login);
        if (user == null || !user.getPassword().equals(Encryptor.encryptSha256(password))) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("User is not authenticated");
            return;
        }

        chain.doFilter(request, response);
    }
}
