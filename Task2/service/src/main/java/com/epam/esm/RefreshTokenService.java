package com.epam.esm;

import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;

/**
 * The interface RefreshTokenService for operations with refresh token
 */
public interface RefreshTokenService {

    /**
     * Search token by its name
     * @param token - the name of token
     * @return - found token
     */
    RefreshTokenDto findByToken(String token);

    /**
     * Create refresh token for logged user
     * @param username - the name of user
     * @return created token
     */
    RefreshTokenDto createRefreshToken(String username);

    /**
     * Validates token
     * @param token
     * @param userDto - the potential owner of token
     */
    void validateToken(RefreshTokenDto token, UserDto userDto);

    /**
     * Updates token in storage
     * @param tokenDto - the token to be updated
     * @return - updated token
     */
    RefreshTokenDto updateToken(RefreshTokenDto tokenDto);
}
