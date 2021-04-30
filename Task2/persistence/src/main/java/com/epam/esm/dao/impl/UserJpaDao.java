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

import org.springframework.stereotype.Repository;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
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

    @Override
    public Optional<User> readRichest() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        Join<User, Order> join = userRoot.join("orders", JoinType.LEFT);
        criteriaQuery.select(userRoot)
                .groupBy(userRoot.get("id"))
                .orderBy(criteriaBuilder.desc(criteriaBuilder.sum(join.get("cost"))));
        TypedQuery<User> query = em.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }
}
