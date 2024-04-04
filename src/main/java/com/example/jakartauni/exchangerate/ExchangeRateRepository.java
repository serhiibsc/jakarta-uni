package com.example.jakartauni.exchangerate;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class ExchangeRateRepository {
    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;

    public void save(ExchangeRate exchangeRate) {
        if (exchangeRate.getId() == null) {
            entityManager.persist(exchangeRate);
        } else {
            entityManager.merge(exchangeRate);
        }
    }

    public ExchangeRate find(Long id) {
        return entityManager.find(ExchangeRate.class, id);
    }

    public void delete(Long id) {
        ExchangeRate exchangeRate = find(id);
        if (exchangeRate != null) {
            entityManager.remove(exchangeRate);
        }
    }

    public List<ExchangeRate> findAll(LocalDate date) {
        TypedQuery<ExchangeRate> query = entityManager.createQuery(
                "SELECT e FROM ExchangeRate e WHERE e.date = :date", ExchangeRate.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    public List<ExchangeRate> findAll(LocalDate startDate, LocalDate endDate) {
        TypedQuery<ExchangeRate> query = entityManager.createQuery("SELECT e FROM ExchangeRate e WHERE e.date BETWEEN :startDate AND :endDate", ExchangeRate.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<ExchangeRate> findRates(String sourceCurrencyName, String targetCurrencyName, LocalDate startDate, LocalDate endDate) {
        TypedQuery<ExchangeRate> query = entityManager.createQuery(
                "SELECT e FROM ExchangeRate e " +
                        "JOIN e.sourceCurrency source " +
                        "JOIN e.targetCurrency target " +
                        "WHERE source.name = :sourceName AND target.name = :targetName AND e.date BETWEEN :startDate AND :endDate",
                ExchangeRate.class);
        query.setParameter("sourceName", sourceCurrencyName);
        query.setParameter("targetName", targetCurrencyName);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}