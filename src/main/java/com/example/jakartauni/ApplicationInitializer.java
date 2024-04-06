package com.example.jakartauni;

import com.example.jakartauni.currency.Currency;
import com.example.jakartauni.currency.CurrencyService;
import com.example.jakartauni.exchangerate.ExchangeRate;
import com.example.jakartauni.exchangerate.ExchangeRateService;
import com.example.jakartauni.user.User;
import com.example.jakartauni.user.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Singleton
@Startup
public class ApplicationInitializer {
    @EJB
    private UserService userService;
    @EJB
    private CurrencyService currencyService;
    @EJB
    private ExchangeRateService exchangeRateService;

    @PostConstruct
    public void onStartup() {
        Optional<User> userOptional = userService.findByUsername("admin");
        if (userOptional.isEmpty()) {
            userService.createDefaultAdmin();
        }

        loadCurrencyData();
        loadExchangeRateData();
        loadExchangeRateDataForToday();
    }

    private void loadExchangeRateDataForToday() {
        InputStream stream = getClass().getResourceAsStream("/META-INF/exchange_rates_today.csv");

        List<String> lines =
                new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream), UTF_8))
                        .lines()
                        .skip(1)
                        .collect(Collectors.toList());
        Collections.shuffle(lines);
        lines.stream()
                .map(line -> line.split(","))
                .map(this::createExchangeRateFromCsvForToday)
                .forEach(exchangeRateService::createExchangeRate);
    }

    private void loadCurrencyData() {
        InputStream stream = getClass().getResourceAsStream("/META-INF/currency.csv");

        new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream), UTF_8))
                .lines()
                .skip(1)
                .map(line -> line.split(","))
                .map(this::createCurrencyFromCsv)
                .forEach(currencyService::createCurrency);
    }

    private void loadExchangeRateData() {
        InputStream stream = getClass().getResourceAsStream("/META-INF/exchange_rate.csv");

        new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream), UTF_8))
                .lines()
                .skip(1)
                .map(line -> line.split(","))
                .map(this::createExchangeRateFromCsv)
                .forEach(exchangeRateService::createExchangeRate);
    }

    private ExchangeRate createExchangeRateFromCsvForToday(String[] fields) {
        Currency target = currencyService.findCurrencyByAbbreviation(fields[2]).orElseThrow();
        Currency source = currencyService.findCurrencyByAbbreviation(fields[3]).orElseThrow();
        return ExchangeRate.builder()
                .date(LocalDate.now())
                .rate(new BigDecimal(fields[1]))
                .sourceCurrency(source)
                .targetCurrency(target)
                .build();
    }

    private ExchangeRate createExchangeRateFromCsv(String[] fields) {
        Currency target = currencyService.findCurrencyByAbbreviation(fields[2]).orElseThrow();
        Currency source = currencyService.findCurrencyByAbbreviation(fields[3]).orElseThrow();
        return ExchangeRate.builder()
                .date(LocalDate.parse(fields[0]))
                .rate(new BigDecimal(fields[1]))
                .sourceCurrency(source)
                .targetCurrency(target)
                .build();
    }

    private Currency createCurrencyFromCsv(String[] fields) {
        return Currency.builder()
                .name(fields[0])
                .abbreviation(fields[1])
                .build();
    }
}