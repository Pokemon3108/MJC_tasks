package com.epam.esm;

import com.epam.esm.entity.User;

/**
 * Service defines operations of user
 */
public interface UserService {

    /**
     * Read user by id
     *
     * @param id - the user id
     * @return filled user dto
     */
    User read(Long id);

}
