package com.epam.esm.dao.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;

@Repository
public class UserJpaDao implements UserDao {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {

        this.em = em;
    }

    @Override
    public Optional<User> read(long id) {

        return Optional.ofNullable(em.find(User.class, id));
    }
}
