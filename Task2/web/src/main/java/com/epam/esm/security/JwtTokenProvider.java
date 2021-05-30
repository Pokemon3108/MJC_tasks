package com.epam.esm.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@PropertySource("classpath:application.properties")
public class JwtTokenProvider {

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String AUTH_HEADER = "Authorization";

    @Value("${security.jwt.token.expire-length-min}")
    private long tokenLifeTime;

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    private UserDetailsService userService;

    @Autowired
    public JwtTokenProvider(@Qualifier("userServiceImpl") UserDetailsService userService) {

        this.userService = userService;
    }

    @PostConstruct
    protected void init() {

        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public long getTokenLifeTime() {

        return tokenLifeTime;
    }

    public String createToken(String username, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(tokenLifeTime)
                .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String loadToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTH_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateToken(String token) {

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        final Date now = new Date();
        return claims.getBody().getExpiration().after(now);
    }

    public String getUsername(String token) {

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
