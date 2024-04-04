package com.example.jakartauni.command.concretecommand;

import com.example.jakartauni.command.Command;
import com.example.jakartauni.command.CommandName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.jakartauni.command.CommandName.UNKNOWN;

public final class UnknownCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("errorMessage", "Requested action is unknown");
        req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
    }

    @Override
    public CommandName getCommandName() {
        return UNKNOWN;
    }
}
