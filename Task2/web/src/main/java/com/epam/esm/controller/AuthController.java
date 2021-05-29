package com.epam.esm.controller;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.RefreshTokenService;
import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.JwtResponse;
import com.epam.esm.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager,
            RefreshTokenService refreshTokenService) {

        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody UserDto authData) {

        String username = authData.getUsername();
        String password = authData.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String jwt = jwtTokenProvider.createToken(username, new ArrayList<>());

        RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(username);

        return new JwtResponse(username, jwt, refreshToken.getToken(), jwtTokenProvider.getTokenLifeTime());
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(@AuthenticationPrincipal UserDetails userDetails) {

        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList())
        );
        return ResponseEntity.ok(model);
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(HttpServletRequest request, @RequestBody UserDto userDto) {

        String requestRefreshToken = request.getAttribute("token").toString();

        RefreshTokenDto tokenDto = refreshTokenService.findByToken(requestRefreshToken);
        refreshTokenService.validateToken(tokenDto, userDto);
        RefreshTokenDto newTokenDto = refreshTokenService.updateToken(tokenDto);

        String username = tokenDto.getUser().getUsername();
        String token = jwtTokenProvider.createToken(username, new ArrayList<>(tokenDto.getUser().getRoles()));
        return new JwtResponse(username, token, newTokenDto.getToken(), jwtTokenProvider.getTokenLifeTime());
    }

}
