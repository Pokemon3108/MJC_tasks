package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.RefreshTokenService;
import com.epam.esm.UserService;
import com.epam.esm.dao.RefreshTokenDao;
import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.RefreshTokenException;

@Service
@PropertySource("classpath:application.properties")
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${security.refreshToken.expire-length-min}")
    private Long refreshTokenLifeTime;

    private UserService userService;

    private RefreshTokenDao refreshTokenDao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public RefreshTokenServiceImpl(UserService userService, RefreshTokenDao refreshTokenDao,
            PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.refreshTokenDao = refreshTokenDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RefreshTokenDto findByToken(String token) {

        return refreshTokenDao.findByToken(token)
                .orElseThrow(() -> new RefreshTokenException("no_refresh_token", token));
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(noRollbackFor = {RefreshTokenException.class})
    public void validateToken(RefreshTokenDto token, UserDto userDto) {

        validateUsersToken(token.getToken(), userDto);
        deleteTokenIfExpires(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public RefreshTokenDto updateToken(RefreshTokenDto tokenDto) {

        final Date exp = Date.from(LocalDateTime.now().plusMinutes(refreshTokenLifeTime)
                .atZone(ZoneId.systemDefault()).toInstant());
        tokenDto.setExpireDate(exp);

        final String newToken = UUID.randomUUID().toString();
        tokenDto.setToken(newToken);

        refreshTokenDao.update(tokenDto);
        return findByToken(newToken);
    }

    /**
     * Checks if token belongs to {@code userDto}
     *
     * @param token
     * @param userDto
     */
    private void validateUsersToken(String token, UserDto userDto) {

        UserDto userFromStorage = userService.read(userDto.getUsername());

        String codedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(codedPassword);

        RefreshTokenDto refreshTokenDto = findByToken(token);
        UserDto tokenUser = refreshTokenDto.getUser();
        if (!tokenUser.equals(userFromStorage)) {
            throw new RefreshTokenException("no_refresh_token", token);
        }
    }

    /**
     * Delete token if it has been expired
     *
     * @param token - to be checked and probably deleted
     */
    private void deleteTokenIfExpires(RefreshTokenDto token) {

        if (token.getExpireDate().before(new Date())) {
            refreshTokenDao.delete(token);
            throw new RefreshTokenException("token_expired", token.getToken());
        }
    }
}
