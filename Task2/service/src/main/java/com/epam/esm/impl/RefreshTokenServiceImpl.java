package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.RefreshTokenService;
import com.epam.esm.UserService;
import com.epam.esm.dao.RefreshTokenDao;
import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;

@Service
@PropertySource("classpath:application.properties")
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${security.refreshToken.expire-length-min}")
    private Long refreshTokenLifeTime;

    private UserService userService;

    private RefreshTokenDao refreshTokenDao;

    @Autowired
    public RefreshTokenServiceImpl(UserService userService, RefreshTokenDao refreshTokenDao) {

        this.userService = userService;
        this.refreshTokenDao = refreshTokenDao;
    }

    @Override
    public Optional<RefreshTokenDto> findByToken(String token) {

        return refreshTokenDao.findByToken(token);
    }

    @Transactional
    @Override
    public RefreshTokenDto createRefreshToken(String username) {

        RefreshTokenDto refreshToken = new RefreshTokenDto();

        UserDto userDto = userService.read(username);
        refreshToken.setUser(userDto);

        Date exp = Date.from(LocalDateTime.now().plusMinutes(refreshTokenLifeTime)
                .atZone(ZoneId.systemDefault()).toInstant());
        refreshToken.setExpireDate(exp);

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenDao.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshTokenDto validateExpiration(RefreshTokenDto token) {

        if (token.getExpireDate().before(new Date())) {
            refreshTokenDao.delete(token);
            throw new RuntimeException("refresh token validation error");
            //TODO throw custom exception
        }

        return token;
    }
}
