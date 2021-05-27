package com.epam.esm.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class RefreshTokenDto {

    private long id;

    private UserDto user;

    private String token;

    private Date expireDate;

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public UserDto getUser() {

        return user;
    }

    public void setUser(UserDto user) {

        this.user = user;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public Date getExpireDate() {

        return expireDate;
    }

    public void setExpireDate(Date expireDate) {

        this.expireDate = expireDate;
    }
}
