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
import java.util.Arrays;
import java.util.List;

import static com.example.jakartauni.command.CommandName.MAIN_PAGE;

@Stateless(name = "MainPageCommand")
public class MainPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Currency usd = Currency.builder().abbreviation("USD").name("US Dollar").build();
        Currency eur = Currency.builder().abbreviation("EUR").name("Euro").build();
        Currency gbp = Currency.builder().abbreviation("GBP").name("British Pound").build();
        Currency uah = Currency.builder().abbreviation("UAH").name("Ukrainian Hryvnia").build();

        List<ExchangeRate> exchangeRates = Arrays.asList(
                ExchangeRate.builder().rate(new BigDecimal("1.85")).targetCurrency(usd).sourceCurrency(eur).build(),
                ExchangeRate.builder().rate(new BigDecimal("0.75")).targetCurrency(usd).sourceCurrency(gbp).build(),
                ExchangeRate.builder().rate(new BigDecimal("0.88")).targetCurrency(eur).sourceCurrency(gbp).build(),
                ExchangeRate.builder().rate(new BigDecimal("42")).targetCurrency(uah).sourceCurrency(eur).build()
        );

        req.setAttribute("exchangeRates", exchangeRates);
        req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, resp);
    }

    @Override
    public CommandName getCommandName() {
        return MAIN_PAGE;
    }


}
