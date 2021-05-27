package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.RefreshToken;

public interface RefreshTokenDao {

    Optional<RefreshToken> findByToken(String token);

}
