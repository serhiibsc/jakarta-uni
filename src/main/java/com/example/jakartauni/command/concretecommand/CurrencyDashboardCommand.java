package com.example.jakartauni.command.concretecommand;

import com.example.jakartauni.command.Command;
import com.example.jakartauni.command.CommandName;
import com.example.jakartauni.currency.Currency;
import com.example.jakartauni.currency.CurrencyService;
import com.example.jakartauni.exchangerate.ExchangeRate;
import com.example.jakartauni.exchangerate.ExchangeRateService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.example.jakartauni.command.CommandName.CURRENCY_DASHBOARD;

@Stateless(name = "CurrencyDashboardCommand")
public final class CurrencyDashboardCommand implements Command {
    @EJB
    private ExchangeRateService exchangeRateService;
    @EJB
    private CurrencyService currencyService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Currency targetCurrency = currencyService.findCurrencyByName(req.getParameter("target"));
        Currency sourceCurrency = currencyService.findCurrencyByName(req.getParameter("source"));
        LocalDate startDate = LocalDate.parse(req.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(req.getParameter("endDate"));
        List<ExchangeRate> exchangeRates = exchangeRateService.findAllRates(sourceCurrency, targetCurrency, startDate, endDate);

        req.setAttribute("targetCurrency", targetCurrency);
        req.setAttribute("sourceCurrency", sourceCurrency);
        req.setAttribute("exchangeRates", exchangeRates);

        req.getRequestDispatcher("/WEB-INF/jsp/currencyDashboard.jsp").forward(req, resp);
    }

    @Override
    public CommandName getCommandName() {
        return CURRENCY_DASHBOARD;
    }
}
