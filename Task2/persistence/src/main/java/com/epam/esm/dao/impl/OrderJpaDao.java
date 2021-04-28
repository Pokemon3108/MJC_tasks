package com.epam.esm.dao.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;

/**
 * Jpa implementation of order dao
 */
@Repository
public class OrderJpaDao implements OrderDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public OrderJpaDao(EntityManager em) {

        this.em = em;
    }

    @Override
    public Optional<Order> create(Order order) {

        em.persist(order);
        return Optional.ofNullable(order);
    }


}
