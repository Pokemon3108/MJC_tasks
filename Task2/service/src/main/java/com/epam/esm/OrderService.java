package com.epam.esm;

import com.epam.esm.entity.Order;

/**
 * Interface for work with orders
 */
public interface OrderService {

    /**
     * Create order
     *
     * @param userId        - the id of user
     * @param certificateId - the id of certificate
     * @return dto object of created order
     */
    Order makeOrder(Long userId, Long certificateId);
}
