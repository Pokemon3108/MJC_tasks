package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.UserService;
import com.epam.esm.model.UserModel;
import com.epam.esm.model.assembler.UserModelAssembler;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private UserModelAssembler userModelAssembler;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler) {

        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("/{id}")
    public UserModel read(@PathVariable long id) {

        return userModelAssembler.toModel(userService.read(id));
    }
}
