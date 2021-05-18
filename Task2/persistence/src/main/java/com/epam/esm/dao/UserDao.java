package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.dto.UserDto;

/**
 * Interface for work with user entities
 */
public interface UserDao {

    Optional<UserDto> read(long id);

    Optional<UserDto> readRichest();

}
