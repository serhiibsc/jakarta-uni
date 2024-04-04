package com.example.jakartauni.currency;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.Optional;

@Stateless
public class CurrencyService {
    @EJB
    private CurrencyRepository currencyRepository;

    public void createCurrency(Currency currency) {
        currency.setId(null);
        currencyRepository.save(currency);
    }

    public void deleteCurrency(Currency currency) {
        currencyRepository.delete(currency.getId());
    }

    public void updateCurrency(Long id, Currency currency) {
        currency.setId(id);
        currencyRepository.save(currency);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> findCurrencyByName(String currencyName) {
        return currencyRepository.findByName(currencyName);
    }

    public Optional<Currency> findCurrencyById(Long id) {
        return currencyRepository.find(id);
    }

    public Optional<Currency> findCurrencyByAbbreviation(String abbreviation) {
        return currencyRepository.findByAbbreviation(abbreviation);
    }
}
