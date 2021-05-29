package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.dto.RefreshTokenDto;

/**
 * Interface for work with storage for refresh tokens.
 */
public interface RefreshTokenDao {

    /**
     * Saves token in storage
     * @param refreshTokenDto - dto of token to be saved
     * @return - saved dto token
     */
    RefreshTokenDto save(RefreshTokenDto refreshTokenDto);

    /**
     * Search token by its name
     * @param token - the name of token
     * @return - found token or {@code Optional.empty()} if it is not found
     */
    Optional<RefreshTokenDto> findByToken(String token);

    /**
     * Deletes token from storage
     * @param refreshTokenDto - dto token to be deleted
     */
    void delete(RefreshTokenDto refreshTokenDto);

    /**
     * Updates token
     * @param refreshTokenDto - token to be updated
     */
    void update(RefreshTokenDto refreshTokenDto);
}
