package com.epam.esm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.OrderService;
import com.epam.esm.entity.Order;

/**
 * OrderController - REST controller for operations with orders
 */

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    /**
     * Creates user's order
     *
     * @param userId        the id of user
     * @param certificateId the id of certificate
     * @return created order
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestParam Long userId, @RequestParam Long certificateId) {

        return orderService.makeOrder(userId, certificateId);
    }
}
