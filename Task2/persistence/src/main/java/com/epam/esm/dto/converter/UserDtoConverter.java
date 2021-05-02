package com.epam.esm.dto.converter;

import org.springframework.stereotype.Component;

import com.epam.esm.entity.User;

/**
 * The type User dto converter for converting from entity to dto
 */
@Component
public class UserDtoConverter extends Converter<User, com.epam.esm.dto.UserDto> {

    @Override
    public com.epam.esm.dto.UserDto convertToDto(User user) {

        if (user == null) {
            return null;
        }
        com.epam.esm.dto.UserDto userDto = new com.epam.esm.dto.UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }

    @Override
    public User convertToEntity(com.epam.esm.dto.UserDto dto) {

        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        return user;
    }


}
