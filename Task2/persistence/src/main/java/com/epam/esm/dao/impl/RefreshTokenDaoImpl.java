package com.epam.esm.dao.impl;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.RefreshTokenDao;
import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.converter.RefreshTokenDtoConverter;
import com.epam.esm.entity.RefreshToken;
import com.epam.esm.entity.User;

@Repository
public class RefreshTokenDaoImpl implements RefreshTokenDao {

    private EntityManager em;

    private RefreshTokenDtoConverter tokenDtoConverter;

    @Autowired
    public RefreshTokenDaoImpl(RefreshTokenDtoConverter tokenDtoConverter) {

        this.tokenDtoConverter = tokenDtoConverter;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {

        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RefreshTokenDto save(RefreshTokenDto refreshTokenDto) {

        RefreshToken refreshToken = tokenDtoConverter.convertToEntity(refreshTokenDto);
        em.persist(refreshToken);
        return tokenDtoConverter.convertToDto(refreshToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RefreshTokenDto> findByToken(String token) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<RefreshToken> criteriaQuery = criteriaBuilder.createQuery(RefreshToken.class);
        Root<RefreshToken> root = criteriaQuery.from(RefreshToken.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("token"), token));

        TypedQuery<RefreshToken> query = em.createQuery(criteriaQuery);
        return query.getResultList()
                .stream()
                .findFirst()
                .map(t -> tokenDtoConverter.convertToDto(t));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(RefreshTokenDto refreshTokenDto) {

        RefreshToken refreshToken = em.find(RefreshToken.class, refreshTokenDto.getId());
        em.remove(refreshToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(RefreshTokenDto refreshTokenDto) {

        em.merge(tokenDtoConverter.convertToEntity(refreshTokenDto));
    }

    @Override
    public Optional<RefreshTokenDto> findByUserId(Long userId) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<RefreshToken> criteriaQuery = criteriaBuilder.createQuery(RefreshToken.class);
        Root<RefreshToken> root = criteriaQuery.from(RefreshToken.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), userId));

        TypedQuery<RefreshToken> query = em.createQuery(criteriaQuery);
        return query.getResultList()
                .stream()
                .findFirst()
                .map(t -> tokenDtoConverter.convertToDto(t));
    }
}
