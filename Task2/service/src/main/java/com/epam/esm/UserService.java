package com.epam.esm;

import com.epam.esm.dto.UserDto;

/**
 * Service defines operations of user
 */
public interface UserService {

    /**
     * Read user by id
     *
     * @param id - the user id
     * @return user
     */
    UserDto read(Long id);

    /**
     * Read user by name
     * @param username - the name of user
     * @return user
     */
    UserDto read(String username);

}
