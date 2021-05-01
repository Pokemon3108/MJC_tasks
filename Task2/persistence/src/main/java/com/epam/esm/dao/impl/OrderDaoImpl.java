package com.epam.esm.dao.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.OrderDtoConverter;
import com.epam.esm.entity.Order;

/**
 * Jpa implementation of order dao
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    private OrderDtoConverter orderDtoConverter;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public OrderDaoImpl(EntityManager em, OrderDtoConverter orderDtoConverter) {

        this.em = em;
        this.orderDtoConverter = orderDtoConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderDto> create(OrderDto orderDto) {

        Order order = orderDtoConverter.convertToEntity(orderDto);
        em.persist(order);
        return Optional.ofNullable(orderDtoConverter.convertToDto(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderDto> read(Long id) {

        return Optional.ofNullable(orderDtoConverter.convertToDto(em.find(Order.class, id)));
    }


}
