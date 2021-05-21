package com.epam.esm.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.UserDto;
import com.epam.esm.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsService userService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
            @Qualifier("userServiceImpl") UserDetailsService userService) {

        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto authData) {

        String username = authData.getName();
        String password = authData.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtTokenProvider.createToken(username, new ArrayList<>());
        return ResponseEntity.ok(token);
    }

}
