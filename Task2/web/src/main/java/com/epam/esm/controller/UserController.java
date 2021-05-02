package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.OrderService;
import com.epam.esm.UserService;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.UserModel;
import com.epam.esm.model.assembler.OrderModelAssembler;
import com.epam.esm.model.assembler.UserModelAssembler;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private UserModelAssembler userModelAssembler;

    private OrderModelAssembler orderModelAssembler;

    private OrderService orderService;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler,
            OrderModelAssembler orderModelAssembler, OrderService orderService) {

        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.orderModelAssembler = orderModelAssembler;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public UserModel read(@PathVariable long id) {

        return userModelAssembler.toModel(userService.read(id));
    }

    @GetMapping("/{userId}/orders")
    public CollectionModel<OrderModel> getOrders(@PathVariable Long userId, @RequestParam Integer page,
            @RequestParam Integer size) {

        return orderModelAssembler.toCollectionModel(orderService.readUserOrders(userId, page, size));
    }
}
