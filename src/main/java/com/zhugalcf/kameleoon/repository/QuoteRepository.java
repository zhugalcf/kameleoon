package com.zhugalcf.kameleoon.repository;

import com.zhugalcf.kameleoon.entity.Quote;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM quotes ORDER BY RAND() LIMIT 1;")
    Optional<Quote> findRandomQuote();

    default List<Quote> findLastQuotes(int quotesQuantity, EntityManager entityManager) {
        TypedQuery<Quote> query = entityManager.createQuery(
                "SELECT q FROM Quote q ORDER BY q.createdAt DESC", Quote.class);
        query.setMaxResults(quotesQuantity);
        return query.getResultList();
    }
    default List<Quote> findTopQuotes(int quotesQuantity, EntityManager entityManager) {
        TypedQuery<Quote> query = entityManager.createQuery(
                "SELECT q FROM Quote q ORDER BY q.score DESC", Quote.class);
        query.setMaxResults(quotesQuantity);
        return query.getResultList();
    }
    default List<Quote> findFlopQuotes(int quotesQuantity, EntityManager entityManager) {
        TypedQuery<Quote> query = entityManager.createQuery(
                "SELECT q FROM Quote q ORDER BY q.score", Quote.class);
        query.setMaxResults(quotesQuantity);
        return query.getResultList();
    }
}
