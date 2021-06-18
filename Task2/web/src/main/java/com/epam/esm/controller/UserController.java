package com.epam.esm.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.epam.esm.validator.UserValidator;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private UserModelAssembler userModelAssembler;

    private OrderModelAssembler orderModelAssembler;

    private OrderService orderService;

    private PageService pageService;

    private UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler,
            OrderModelAssembler orderModelAssembler, OrderService orderService,
            PageService pageService, UserValidator userValidator) {

        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.orderModelAssembler = orderModelAssembler;
        this.orderService = orderService;
        this.pageService = pageService;
        this.userValidator = userValidator;
    }

    @GetMapping(value = {"/{id}", "/**"})
    public UserModel read(@AuthenticationPrincipal String username, @PathVariable(required = false) Long id) {

        if (id == null) {
            id = userService.read(username).getId();
        }
        return userModelAssembler.toModel(userService.read(id));
    }

    @PostMapping
    public UserModel create(@RequestBody UserDto userDto) {

        userValidator.validatePassword(userDto.getPassword());
        return userModelAssembler.toModel(userService.create(userDto));
    }

    @GetMapping(value = {"/{userId}/orders", "/orders"})
    public PageOrderModel getOrders(@AuthenticationPrincipal String username,
            @PathVariable(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size) {

        UserDto userDto;
        if (userId != null) {
            userDto = userService.read(userId);
        } else {
            userDto = userService.read(username);
            userId = userDto.getId();
        }

        Set<OrderDto> orders = orderService.readUserOrders(userId, page, size);
        return new PageOrderModel(
                orderModelAssembler.toCollectionModel(orders),
                pageService.buildPageForUserOrderSearch(page, size, userDto, new ArrayList<>(orders)));

    }
}
