package com.epam.esm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.UserService;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.UserDtoConverter;
import com.epam.esm.exception.user.DuplicateUserException;
import com.epam.esm.exception.user.NoUserWithIdException;


/**
 * Implementation of user service
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDao userDao;

    private UserDtoConverter userDtoConverter;

    private PasswordEncoder passwordEncoder;

    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    public UserServiceImpl(UserDao userDao, UserDtoConverter userDtoConverter, @Lazy PasswordEncoder passwordEncoder) {

        this.userDao = userDao;
        this.userDtoConverter = userDtoConverter;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {

        String username = userDto.getUsername();
        if (userDao.read(username).isPresent()) {
            throw new DuplicateUserException(username);
        }

        String codedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(codedPassword);
        userDto.setRole(ROLE_USER);
        return userDao.create(userDto);
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
