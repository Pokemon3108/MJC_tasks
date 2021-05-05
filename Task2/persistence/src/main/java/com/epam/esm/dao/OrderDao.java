package com.epam.esm.dao;

import java.util.Optional;
import java.util.Set;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

/**
 * Interface for access to database for working with orders
 */
public interface OrderDao {

    /**
     * Create order
     *
     * @param order - the order to be saved in storage
     * @return the id of created order
     */
    Long create(OrderDto order);

    /**
     * Read order from storage by id
     *
     * @param id - the id of order
     * @return read order from storage, or {@code Optional.empty()} if it doesn't exist
     */
    Optional<OrderDto> read(Long id);

    /**
     * Read user's orders
     * @param user
     * @param page number of page starting from 1
     * @param size amount of orders in one page
     * @return set of user's orders
     */
    Set<OrderDto> readUserOrder(UserDto user, int page, int size);
}
