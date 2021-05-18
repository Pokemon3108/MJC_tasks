package com.epam.esm.impl;

import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.exception.user.NoUserWithIdException;
import com.epam.esm.exception.user.NoUsersException;

class UserServiceImplTest {

    UserDaoImpl userDao;

    UserServiceImpl userService;

    @BeforeEach
    void init() {

        userDao = mock(UserDaoImpl.class);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    void readThrowsExceptionTest() {

        final long id = 900L;
        Mockito.when(userDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoUserWithIdException.class, () -> userService.read(id));
    }

    @Test
    void readRichestThrowsException() {

        Mockito.when(userDao.readRichest()).thenReturn(Optional.empty());
        Assertions.assertThrows(NoUsersException.class, () -> userService.readRichest());
    }

}
