package com.example.jakartauni.exchangerate;

import com.example.jakartauni.currency.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exchangerate",
        uniqueConstraints = @UniqueConstraint(columnNames = {"source_currency_id",
                "target_currency_id", "date"}))
public class ExchangeRate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "source_currency_id", referencedColumnName = "id")
    private Currency sourceCurrency;

    @ManyToOne
    @JoinColumn(name = "target_currency_id", referencedColumnName = "id")
    private Currency targetCurrency;

    @Column(precision = 15, scale = 2)
    private BigDecimal rate;

    private LocalDate date;
}
