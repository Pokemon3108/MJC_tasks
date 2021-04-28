package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.Order;

public interface OrderDao {

    Optional<Order> create(Order order);
}
