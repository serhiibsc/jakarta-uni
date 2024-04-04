package com.example.jakartauni.command.concretecommand;


import com.example.jakartauni.command.Command;
import com.example.jakartauni.command.CommandName;
import com.example.jakartauni.currency.CurrencyService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Stateless(name = "CurrenciesPageCommand")
public class CurrenciesPageCommand implements Command {
    @EJB
    private CurrencyService currencyService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("currencies", currencyService.findAll());
        req.getRequestDispatcher("/WEB-INF/jsp/currencies.jsp").forward(req, resp);
    }

    @Override
    public CommandName getCommandName() {
        return CommandName.CURRENCIES;
    }
}
