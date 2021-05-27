package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.dto.RefreshTokenDto;

public interface RefreshTokenDao {

    RefreshTokenDto save(RefreshTokenDto refreshTokenDto);

    Optional<RefreshTokenDto> findByToken(String token);

    void delete(RefreshTokenDto refreshTokenDto);

}
