package com.example.jakartauni.command.concretecommand;

import com.example.jakartauni.command.Command;
import com.example.jakartauni.command.CommandName;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Stateless(name = "LoginCommand")
public final class LoginCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // assuming all the logic done in Custom Auth Mechanism
        resp.sendRedirect(req.getContextPath() + "/controller?command=main_page");
    }

    @Override
    public CommandName getCommandName() {
        return CommandName.LOGIN;
    }
}
