package com.epam.esm;

import java.util.Set;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

/**
 * Interface for work with orders
 */
public interface OrderService {

    /**
     * Create order
     *
     * @param userId        - the id of user
     * @param certificateId - the id of certificate
     * @return the id of created order
     */
    Long makeOrder(Long userId, Long certificateId);

    /**
     * Read order by id
     *
     * @param orderId - the id of order
     * @return order from storage
     */
    OrderDto read(Long orderId);

    /**
     * Read user's orders
     *
     * @param userId - the id of user
     * @param page   - number of page starting from 1
     * @param size   - maximum amount of orders in result set
     * @return set of user's orders
     */
    Set<OrderDto> readUserOrders(long userId, int page, int size);

    /**
     *
     * @param user - the user, whose orders are counted
     * @return total amount of user's orders
     */
    long countUserOrders(UserDto user);
}
