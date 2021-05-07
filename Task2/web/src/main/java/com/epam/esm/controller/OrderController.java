package com.epam.esm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.OrderService;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.assembler.OrderModelAssembler;

/**
 * OrderController - REST controller for operations with orders
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    private OrderModelAssembler orderModelAssembler;

    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler) {

        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
    }

    /**
     * Creates user's order
     *
     * @param userId        the id of user
     * @param certificateId the id of certificate
     * @return created order with hateoas links
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel create(@RequestParam Long userId, @RequestParam Long certificateId) {

        return orderModelAssembler.toModel(orderService.makeOrder(userId, certificateId));
    }

    /**
     * Read order by id
     *
     * @param id - the id of order
     * @return order model with hateoas links
     */
    @GetMapping("/{id}")
    public OrderModel read(@PathVariable Long id) {

        return orderModelAssembler.toModel(orderService.read(id));
    }

}
