package com.epam.esm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.esm.UserService;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.UserDtoConverter;
import com.epam.esm.exception.user.NoUserWithIdException;


/**
 * Implementation of user service
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDao userDao;

    private UserDtoConverter userDtoConverter;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserDtoConverter userDtoConverter) {

        this.userDao = userDao;
        this.userDtoConverter = userDtoConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto read(Long id) {

        return userDao.read(id).orElseThrow(() -> new NoUserWithIdException(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto read(String username) {

        return userDtoConverter.convertToDto(userDao.read(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        return userDao.read(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
