package com.epam.esm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Configures security
 */
@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenFilter tokenFilter;

    @Autowired
    public JwtConfigurer(TokenFilter tokenFilter) {

        this.tokenFilter = tokenFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(HttpSecurity http) {

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

