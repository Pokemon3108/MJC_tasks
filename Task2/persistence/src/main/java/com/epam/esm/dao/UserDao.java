package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;

/**
 * Interface for work with user entities
 */
public interface UserDao {

    /**
     * Read user by id
     * @param id
     * @return user from storage or {@code Optional.empty()} if it is not found
     */
    Optional<UserDto> read(long id);

    /**
     * Read user by name
     * @param username
     * @return user from storage or {@code Optional.empty()} if it is not found
     */
    Optional<User> read(String username);

    /**
     * Saves user in storage
     * @param userDto to be saved
     * @return created user
     */
    UserDto create(UserDto userDto);

}
