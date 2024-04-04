package com.example.jakartauni.exchangerate;

import com.example.jakartauni.currency.Currency;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class ExchangeRateService {
    @EJB
    private ExchangeRateRepository exchangeRateRepository;

    public void createExchangeRate(ExchangeRate exchangeRate) {
        exchangeRateRepository.save(exchangeRate);
    }

    public List<ExchangeRate> findAllRates(LocalDate date) {
        return exchangeRateRepository.findAll(date);
    }

    public List<ExchangeRate> findAllRates(
            Currency from, Currency to, LocalDate startDate, LocalDate endDate) {
        return exchangeRateRepository.findRates(from.getName(), to.getName(), startDate, endDate);
    }
}
