package com.epam.esm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.epam.esm.RefreshTokenService;
import com.epam.esm.dto.RefreshTokenDto;

@Component
public class RefreshTokenProvider {

    private UserDetailsService userDetailsService;

    private RefreshTokenService tokenService;

    @Autowired
    public RefreshTokenProvider(@Qualifier("userServiceImpl") UserDetailsService userDetailsService,
            @Lazy RefreshTokenService tokenService) {

        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    public String getUsername(String token) {

        RefreshTokenDto tokenDto = tokenService.findByToken(token);
        return tokenDto.getUser().getUsername();
    }

    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
