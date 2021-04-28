package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.User;

/**
 * Interface for work with user entities
 */
public interface UserDao {

    Optional<User> read(long id);

}
