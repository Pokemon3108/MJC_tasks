package com.epam.esm.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.RefreshToken;
import com.epam.esm.entity.User;

@Component
public class RefreshTokenDtoConverter extends Converter<RefreshToken, RefreshTokenDto> {

    private UserDtoConverter userDtoConverter;

    @Autowired
    public RefreshTokenDtoConverter(UserDtoConverter userDtoConverter) {

        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public RefreshToken convertToEntity(RefreshTokenDto dto) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpireDate(dto.getExpireDate());
        refreshToken.setId(dto.getId());
        refreshToken.setToken(dto.getToken());

        final User user = userDtoConverter.convertToEntity(dto.getUser());
        refreshToken.setUser(user);

        return refreshToken;
    }

    @Override
    public RefreshTokenDto convertToDto(RefreshToken entity) {

        RefreshTokenDto tokenDto = new RefreshTokenDto();
        tokenDto.setExpireDate(entity.getExpireDate());
        tokenDto.setId(entity.getId());
        tokenDto.setToken(entity.getToken());

        final UserDto userDto = userDtoConverter.convertToDto(entity.getUser());
        tokenDto.setUser(userDto);

        return tokenDto;
    }
}
