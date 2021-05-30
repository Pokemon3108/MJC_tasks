package com.epam.esm.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filters authentication tokens
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private RefreshTokenProvider refreshTokenProvider;

    @Autowired
    public TokenFilter(JwtTokenProvider tokenProvider, RefreshTokenProvider refreshTokenProvider) {

        this.jwtTokenProvider = tokenProvider;
        this.refreshTokenProvider = refreshTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = jwtTokenProvider.loadToken(request);
        request.setAttribute("token", token);

        String grantTypeHeader = request.getHeader("grant-type");

        if ((grantTypeHeader != null && grantTypeHeader.equals("refresh_token"))) {

            Authentication auth = refreshTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else if (token != null && jwtTokenProvider.validateToken(token)) {

            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

}

