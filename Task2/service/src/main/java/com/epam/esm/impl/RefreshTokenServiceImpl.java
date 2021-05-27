package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.esm.RefreshTokenService;
import com.epam.esm.dao.RefreshTokenDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.exception.user.NoUserWithIdException;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${security.refreshToken.expire-length-ms}")
    private Long refreshTokenLifeTime;

    private UserDao userDao;

    private RefreshTokenDao refreshTokenDao;

    @Autowired
    public RefreshTokenServiceImpl(UserDao userDao, RefreshTokenDao refreshTokenDao) {

        this.userDao = userDao;
        this.refreshTokenDao = refreshTokenDao;
    }

    @Override
    public Optional<RefreshTokenDto> findByToken(String token) {

        return refreshTokenDao.findByToken(token);
    }

    @Override
    public RefreshTokenDto createRefreshToken(Long userId) {

        RefreshTokenDto refreshToken = new RefreshTokenDto();

        refreshToken.setUser(userDao.read(userId).orElseThrow(() -> new NoUserWithIdException(userId)));

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
