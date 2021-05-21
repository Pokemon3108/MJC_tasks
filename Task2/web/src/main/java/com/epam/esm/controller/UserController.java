package com.epam.esm.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.OrderService;
import com.epam.esm.PageService;
import com.epam.esm.UserService;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.PageOrderModel;
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

    private PageService pageService;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler,
            OrderModelAssembler orderModelAssembler, OrderService orderService, PageService pageService) {

        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.orderModelAssembler = orderModelAssembler;
        this.orderService = orderService;
        this.pageService = pageService;
    }

    @GetMapping("/{id}")
    public UserModel read(@PathVariable long id) {

        return userModelAssembler.toModel(userService.read(id));
    }

    @GetMapping("/{userId}/orders")
    public PageOrderModel getOrders(@PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size) {

        UserDto userDto = userService.read(userId);
        Set<OrderDto> orders = orderService.readUserOrders(userId, page, size);
        return new PageOrderModel(
                orderModelAssembler.toCollectionModel(orders),
                pageService.buildPageForUserOrderSearch(page, size, userDto, new ArrayList<>(orders)));

    }
}
