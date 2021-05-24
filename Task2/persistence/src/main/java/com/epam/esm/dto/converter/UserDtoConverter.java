package com.epam.esm.dto.converter;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;

/**
 * The type User dto converter for converting from entity to dto
 */
@Component
public class UserDtoConverter extends Converter<User, UserDto> {

    @Override
    public UserDto convertToDto(User user) {

        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto dto) {

        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRoles(Collections.singleton(dto.getRole()));
        return user;
    }


}
