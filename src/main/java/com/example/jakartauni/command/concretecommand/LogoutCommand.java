package com.example.jakartauni.command.concretecommand;

import com.example.jakartauni.command.Command;
import com.example.jakartauni.command.CommandName;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Stateless(name = "LogoutCommand")
public final class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.logout();
        resp.sendRedirect(req.getContextPath() + "/controller?command=main_page");
    }

    @Override
    public CommandName getCommandName() {
        return CommandName.LOGOUT;
    }
}
