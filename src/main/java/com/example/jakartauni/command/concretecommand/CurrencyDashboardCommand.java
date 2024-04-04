package com.example.jakartauni.command.concretecommand;

import com.example.jakartauni.command.Command;
import com.example.jakartauni.command.CommandName;
import com.example.jakartauni.currency.Currency;
import com.example.jakartauni.exchangerate.ExchangeRate;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.example.jakartauni.command.CommandName.CURRENCY_DASHBOARD;

@Stateless(name = "CurrencyDashboardCommand")
public final class CurrencyDashboardCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Currency usd = Currency.builder().abbreviation("USD").name("US Dollar").build();
        Currency eur = Currency.builder().abbreviation("EUR").name("Euro").build();

        List<ExchangeRate> exchangeRates = Arrays.asList(
                ExchangeRate.builder()
                        .date(LocalDate.of(2022, 1, 1))
                        .rate(new BigDecimal("0.85"))
                        .targetCurrency(usd)
                        .sourceCurrency(eur)
                        .build(),
                ExchangeRate.builder()
                        .date(LocalDate.of(2022, 1, 2))
                        .rate(new BigDecimal("0.86"))
                        .targetCurrency(usd)
                        .sourceCurrency(eur)
                        .build(),
                ExchangeRate.builder()
                        .date(LocalDate.of(2022, 1, 3))
                        .rate(new BigDecimal("0.87"))
                        .targetCurrency(usd)
                        .sourceCurrency(eur)
                        .build()
        );
        req.setAttribute("selectedCurrency", usd);
        req.setAttribute("exchangeRates", exchangeRates);

        req.getRequestDispatcher("/WEB-INF/jsp/currencyDashboard.jsp").forward(req, resp);
    }

    @Override
    public CommandName getCommandName() {
        return CURRENCY_DASHBOARD;
    }
}
