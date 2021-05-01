package com.epam.esm.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;

/**
 * The type User dto converter for converting from entity to dto
 */
@Component
public class UserDtoConverter extends Converter<User, UserDto> {

    private OrderDtoConverter orderDtoConverter;

    @Autowired
    public UserDtoConverter(OrderDtoConverter orderDtoConverter) {

        this.orderDtoConverter = orderDtoConverter;
    }

    @Override
    public UserDto convertToDto(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setOrders(orderDtoConverter.convertToDtos(user.getOrders()));
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto dto) {

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setOrders(orderDtoConverter.convertToEntities(dto.getOrders()));
        return user;
    }


}
