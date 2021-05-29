package com.epam.esm;

import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;

public interface RefreshTokenService {

    RefreshTokenDto findByToken(String token);

    RefreshTokenDto createRefreshToken(String username);

    RefreshTokenDto validateToken(RefreshTokenDto token, UserDto userDto);

    RefreshTokenDto updateToken(RefreshTokenDto tokenDto);
}
