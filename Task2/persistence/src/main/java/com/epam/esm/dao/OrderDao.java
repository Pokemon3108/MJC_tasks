package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.Order;

/**
 * Interface for access to database for working with orders
 */
public interface OrderDao {

    /**
     * Create order
     * @param order - the order to be saved in storage
     * @return create order from storage, or {@code Optional.empty()} if it was not created
     */
    Optional<Order> create(Order order);

    /**
     * Read order from storage by id
     * @param id - the id of order
     * @return read order from storage, or {@code Optional.empty()} if it doesn't exist
     */
    Optional<Order> read(Long id);
}
