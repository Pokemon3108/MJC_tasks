package com.epam.esm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.esm.UserService;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.user.NoUserWithIdException;
import com.epam.esm.exception.user.NoUsersException;


/**
 * Implementation of user service
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {

        this.userDao = userDao;
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
    public UserDto readRichest() {

        return userDao.readRichest().orElseThrow(NoUsersException::new);
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
