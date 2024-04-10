package com.example.jakartauni.service;

import com.example.jakartauni.entity.Currency;
import com.example.jakartauni.entity.ExchangeRate;
import com.example.jakartauni.repository.ExchangeRateRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Stateless
public class ExchangeRateService {
    @EJB
    private ExchangeRateRepository exchangeRateRepository;

    public ExchangeRate createExchangeRate(ExchangeRate exchangeRate) {
        Optional<ExchangeRate> exchangeRateWithTheSameDate = findWithPairTheSameDate(exchangeRate);

        if (exchangeRateWithTheSameDate.isEmpty()) {
            return exchangeRateRepository.save(exchangeRate);
        }
        exchangeRateWithTheSameDate.get().setRate(exchangeRate.getRate());
        return exchangeRateRepository.save(exchangeRateWithTheSameDate.get());
    }

    public Optional<ExchangeRate> findWithPairTheSameDate(ExchangeRate exchangeRate) {
        List<ExchangeRate> exchangeRates =
                findAllPairs(exchangeRate.getSourceCurrency(), exchangeRate.getTargetCurrency());
        return exchangeRates.stream().filter(er -> er.getDate().isEqual(exchangeRate.getDate())).findFirst();
    }

    public List<ExchangeRate> findAllPairs(Currency from, Currency to) {
        return exchangeRateRepository.findPairs(from.getName(), to.getName());
    }

    public List<ExchangeRate> findAllRates(LocalDate date) {
        return exchangeRateRepository.findAll(date);
    }

    public List<ExchangeRate> findAllRates(
            Currency from, Currency to, LocalDate startDate, LocalDate endDate) {
        return exchangeRateRepository.findRates(from.getName(), to.getName(), startDate, endDate);
    }
}
