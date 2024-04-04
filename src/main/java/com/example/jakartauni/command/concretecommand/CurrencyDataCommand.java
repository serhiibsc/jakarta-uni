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

import static com.example.jakartauni.command.CommandName.CURRENCY_DATA;

@Stateless(name = "CurrencyDataCommand")
public class CurrencyDataCommand implements Command {
    @EJB
    private CurrencyService currencyService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long currencyId = Long.parseLong(req.getParameter("currencyId"));
        req.setAttribute("currency", currencyService.findCurrencyById(currencyId));
        req.getRequestDispatcher("/WEB-INF/jsp/currency.jsp").forward(req, resp);
    }

    @Override
    public CommandName getCommandName() {
        return CURRENCY_DATA;
    }
}
