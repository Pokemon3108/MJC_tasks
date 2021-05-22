package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;

/**
 * Interface for work with user entities
 */
public interface UserDao {

    Optional<UserDto> read(long id);

    Optional<User> read(String username);

}
