package com.example.jakartauni.command.concretecommand;

import com.example.jakartauni.command.Command;
import com.example.jakartauni.command.CommandName;
import com.example.jakartauni.encrypt.Encryptor;
import com.example.jakartauni.user.User;
import com.example.jakartauni.user.UserService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Stateless(name = "LoginCommand")
public final class LoginCommand implements Command {
    @EJB
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            req.setAttribute("errorMessage", "Please enter both login and password.");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }
        if (!login.matches("[a-zA-Z0-9\\-]{3,10}")) {
            req.setAttribute("errorMessage", "Login is suspicious!");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }
        User user = userService.findByUsername(login);

        if (user == null || !user.getPassword().equals(Encryptor.encryptSha256(password))) {
            req.setAttribute("errorMessage", "Invalid login or password.");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/controller?command=main_page");
    }

    @Override
    public CommandName getCommandName() {
        return CommandName.LOGIN;
    }
}
