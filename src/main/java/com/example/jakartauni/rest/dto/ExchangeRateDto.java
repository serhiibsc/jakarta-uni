package com.example.jakartauni.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDto {
    private String sourceCurrencyAbbreviation;
    private String targetCurrencyAbbreviation;
    private BigDecimal rate;
    private LocalDate date;
}
