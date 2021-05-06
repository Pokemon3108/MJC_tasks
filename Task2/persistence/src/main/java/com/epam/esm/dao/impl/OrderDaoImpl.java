package com.epam.esm.dao.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.OrderDtoConverter;
import com.epam.esm.dto.converter.UserDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;

/**
 * Jpa implementation of order dao
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    private OrderDtoConverter orderDtoConverter;

    private UserDtoConverter userDtoConverter;

    private EntityManager em;

    @Autowired
    public OrderDaoImpl(OrderDtoConverter orderDtoConverter, UserDtoConverter userDtoConverter) {

        this.orderDtoConverter = orderDtoConverter;
        this.userDtoConverter = userDtoConverter;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {

        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long create(OrderDto orderDto) {

        Order order = orderDtoConverter.convertToEntity(orderDto);
        em.persist(order);
        return order.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderDto> read(Long id) {

        return Optional.ofNullable(orderDtoConverter.convertToDto(em.find(Order.class, id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<OrderDto> readUserOrder(UserDto user, int page, int size) {

        CriteriaQuery<Order> criteriaQuery = buildQueryForUserOrdersSearch(user);

        TypedQuery<Order> q = em.createQuery(criteriaQuery);
        q.setFirstResult((page - 1) * size);
        q.setMaxResults(size);
        return orderDtoConverter.convertToDtos(q.getResultStream().collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countUserOrders(UserDto user) {

        CriteriaQuery<Order> criteriaQuery = buildQueryForUserOrdersSearch(user);
        TypedQuery<Order> q = em.createQuery(criteriaQuery);
        return q.getResultStream().count();
    }

    /**
     * {@inheritDoc}
     */
    private CriteriaQuery<Order> buildQueryForUserOrdersSearch(UserDto user) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);

        criteriaQuery.select(orderRoot)
                .where(criteriaBuilder.equal(orderRoot.get("user"), userDtoConverter.convertToEntity(user)));
        return criteriaQuery;
    }

}
