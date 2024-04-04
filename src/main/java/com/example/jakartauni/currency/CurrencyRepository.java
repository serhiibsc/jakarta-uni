package com.example.jakartauni.currency;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@Stateless
public class CurrencyRepository {
    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;

    public void save(Currency currency) {
        if (currency.getId() == null || find(currency.getId()).isEmpty()) {
            entityManager.persist(currency);
            return;
        }
        entityManager.merge(currency);
    }

    public Optional<Currency> find(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(Currency.class, id));
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Currency> findAll() {
        TypedQuery<Currency> query = entityManager.createQuery(
                "SELECT e FROM Currency e", Currency.class);
        return query.getResultList();
    }

    public void delete(Long currencyId) {
        find(currencyId).ifPresent(currency -> entityManager.remove(currency));
    }

    public Optional<Currency> findByName(String currencyName) {
        try {
            String query = "SELECT c FROM Currency c WHERE c.name = :currencyName";
            Currency foundCurrency = entityManager.createQuery(query, Currency.class)
                    .setParameter("currencyName", currencyName)
                    .getSingleResult();
            return Optional.ofNullable(foundCurrency);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Currency> findByAbbreviation(String abbreviation) {
        try {
            String query = "SELECT c FROM Currency c WHERE c.abbreviation = :abbreviation";
            Currency foundCurrency = entityManager.createQuery(query, Currency.class)
                    .setParameter("abbreviation", abbreviation)
                    .getSingleResult();
            return Optional.ofNullable(foundCurrency);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}