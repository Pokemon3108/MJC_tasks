package com.epam.esm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
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
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.order.NoOrderException;
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

    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler,
            UserService userService) {

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
    public OrderModel create(@AuthenticationPrincipal String username, @RequestParam Long certificateId) {

        UserDto userDto = userService.read(username);
        return orderModelAssembler.toModel(orderService.makeOrder(userDto.getId(), certificateId));
    }

    /**
     * Read order by id
     *
     * @param id - the id of order
     * @return order model with hateoas links
     */
    @GetMapping("/{id}")
    public OrderModel read(@AuthenticationPrincipal String username, @PathVariable Long id) {

        UserDto userDto = userService.read(username);
        OrderDto orderDto = orderService.read(id);
        if (!userDto.getRoles().contains("ROLE_ADMIN") && !orderDto.getUser().getId().equals(userDto.getId())) {
            throw new NoOrderException(orderDto.getId());
        }
        return orderModelAssembler.toModel(orderDto);
    }

}
