package com.epam.esm.model;

public class JwtResponse {

    private String username;

    private String jwt;

    private String refreshToken;

    public JwtResponse(String username, String jwt, String refreshToken) {

        this.username = username;
        this.jwt = jwt;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getJwt() {

        return jwt;
    }

    public void setJwt(String jwt) {

        this.jwt = jwt;
    }

    public String getRefreshToken() {

        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {

        this.refreshToken = refreshToken;
    }
}
