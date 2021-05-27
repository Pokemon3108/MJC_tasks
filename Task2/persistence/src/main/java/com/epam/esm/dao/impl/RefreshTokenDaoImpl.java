package com.epam.esm.dao.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.epam.esm.dao.RefreshTokenDao;
import com.epam.esm.entity.RefreshToken;

public class RefreshTokenDaoImpl implements RefreshTokenDao {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {

        this.em = em;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<RefreshToken> criteriaQuery = criteriaBuilder.createQuery(RefreshToken.class);
        Root<RefreshToken> root = criteriaQuery.from(RefreshToken.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("token"), token));

        TypedQuery<RefreshToken> query = em.createQuery(criteriaQuery);
        return query.getResultList()
                .stream()
                .findFirst();
    }
}
