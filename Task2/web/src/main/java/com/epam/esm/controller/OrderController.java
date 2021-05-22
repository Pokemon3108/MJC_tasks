package com.epam.esm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.OrderService;
import com.epam.esm.UserService;
import com.epam.esm.dto.UserDto;
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

    private UserService userService;

    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler, UserService userService) {

        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
        this.userService = userService;
    }

    /**
     * Creates user's order
     *
     * @param certificateId the id of certificate
     * @return created order with hateoas links
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel create(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long certificateId) {

        UserDto userDto = userService.read(userDetails.getUsername());
        return orderModelAssembler.toModel(orderService.makeOrder(userDto.getId(), certificateId));
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
