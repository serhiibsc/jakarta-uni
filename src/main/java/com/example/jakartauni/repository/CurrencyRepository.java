package com.example.jakartauni.repository;

import com.example.jakartauni.entity.Currency;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Stateless
@Transactional
public class CurrencyRepository {
    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;

    public void saveTestTransaction(Currency currency) {
        if (currency.getId() == null || find(currency.getId()).isEmpty()) {
            entityManager.persist(currency);
            currency.setName("23");
            saveAndCommitInBoundsOfOneTransaction(currency);
        }
        if (true) {
            throw new IllegalStateException();
        }
    }

    public void saveAndCommitInBoundsOfOneTransaction(Currency currency) {
        entityManager.persist(currency);
    }

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
        find(currencyId).ifPresent(currency -> {
            if (entityManager.contains(currency)) {
                entityManager.remove(currency);
            } else {
                Currency managedCurrency = entityManager.merge(currency);
                entityManager.remove(managedCurrency);
            }
        });
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