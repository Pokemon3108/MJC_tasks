package com.epam.esm.controller.handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.epam.esm.exception.RefreshTokenException;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private HandlerExceptionResolver resolver;

    @Autowired
    public ExceptionHandlerFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {

        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | RefreshTokenException e) {
            resolver.resolveException(request, response, null, e);
        }
    }
}
