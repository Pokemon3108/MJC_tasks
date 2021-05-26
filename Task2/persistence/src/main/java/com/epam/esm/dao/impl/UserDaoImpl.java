package com.epam.esm.dao.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.UserDtoConverter;
import com.epam.esm.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager em;

    private UserDtoConverter userDtoConverter;

    @Autowired
    public UserDaoImpl(UserDtoConverter userDtoConverter) {

        this.userDtoConverter = userDtoConverter;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {

        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserDto> read(long id) {

        return Optional.ofNullable(userDtoConverter.convertToDto(em.find(User.class, id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserDto> read(String username) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("username"), username));

        TypedQuery<User> query = em.createQuery(criteriaQuery);
        return query.getResultList()
                .stream()
                .findFirst()
                .map(user -> userDtoConverter.convertToDto(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto create(UserDto userDto) {

        User user = userDtoConverter.convertToEntity(userDto);
        em.persist(user);
        return userDtoConverter.convertToDto(user);
    }
}
