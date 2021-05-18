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
     * @return filled user
     */
    UserDto read(Long id);

    /**
     * Read user with the highest cost of order
     *
     * @return user from storage
     */
    UserDto readRichest();

}
