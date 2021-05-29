package com.epam.esm;

import java.util.Optional;

import com.epam.esm.dto.RefreshTokenDto;

public interface RefreshTokenService {

    Optional<RefreshTokenDto> findByToken(String token);

    RefreshTokenDto createRefreshToken(String username);

    RefreshTokenDto validateExpiration(RefreshTokenDto token);
}
