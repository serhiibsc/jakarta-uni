package com.example.jakartauni.exchangerate;

import com.example.jakartauni.currency.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    private Long id;

    private Currency sourceCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
    private LocalDate date;
}
