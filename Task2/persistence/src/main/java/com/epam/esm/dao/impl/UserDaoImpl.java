package com.epam.esm.dao.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.UserDtoConverter;
import com.epam.esm.entity.Order;
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

    @Override
    public Optional<UserDto> read(long id) {

        return Optional.ofNullable(userDtoConverter.convertToDto(em.find(User.class, id)));
    }

    @Override
    public Optional<UserDto> readRichest() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        Join<User, Order> join = userRoot.join("orders", JoinType.LEFT);
        criteriaQuery.select(userRoot)
                .groupBy(userRoot.get("id"))
                .orderBy(criteriaBuilder.desc(criteriaBuilder.sum(join.get("cost"))));
        TypedQuery<User> query = em.createQuery(criteriaQuery);
        return query.getResultStream().findFirst().map(user -> userDtoConverter.convertToDto(user));
    }
}
