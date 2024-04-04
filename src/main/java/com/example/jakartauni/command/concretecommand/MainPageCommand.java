package com.example.jakartauni.command.concretecommand;

import com.example.jakartauni.command.Command;
import com.example.jakartauni.command.CommandName;
import com.example.jakartauni.exchangerate.ExchangeRateService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import static com.example.jakartauni.command.CommandName.MAIN_PAGE;

@Stateless(name = "MainPageCommand")
public final class MainPageCommand implements Command {
    @EJB
    private ExchangeRateService exchangeRateService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("exchangeRates", exchangeRateService.findAllRates(LocalDate.now()));
        req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, resp);
    }

    @Override
    public CommandName getCommandName() {
        return MAIN_PAGE;
    }


}
