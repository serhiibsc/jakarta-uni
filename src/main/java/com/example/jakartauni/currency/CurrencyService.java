package com.example.jakartauni.currency;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class CurrencyService {
    @EJB
    private CurrencyRepository currencyRepository;

    public void createCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    public void deleteCurrency(Currency currency) {
        currencyRepository.delete(currency.getId());
    }

    public void updateCurrency(Currency currency) {
        if (Objects.isNull(currency.getName())) {
            throw new IllegalStateException();
        }
        currencyRepository.save(currency);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Currency findCurrencyByName(String currencyName) {
        Optional<Currency> currency = currencyRepository.findByName(currencyName);
        if (currency.isEmpty()) {
            throw new IllegalStateException("No such a currency");
        }
        return currency.get();
    }

    public Optional<Currency> findCurrencyById(Long id) {
        return currencyRepository.find(id);
    }
}
