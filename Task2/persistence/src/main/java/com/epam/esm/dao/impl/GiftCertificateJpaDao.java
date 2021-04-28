package com.epam.esm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.query.DaoQuery;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

/**
 * Jpa implementation of certificate dao
 */
@Repository
@Qualifier("certificateJpaDao")
public class GiftCertificateJpaDao implements GiftCertificateDao {

    private GiftCertificateDtoConverter converter;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public GiftCertificateJpaDao(EntityManager em, GiftCertificateDtoConverter converter) {

        this.em = em;
        this.converter = converter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long insert(GiftCertificateDto certificateDto) {

        GiftCertificate certificate = converter.convertToEntity(certificateDto);
        em.persist(certificate);
        return certificate.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(GiftCertificateDto certificateDto) {

        GiftCertificate certificateToBeUpdated = converter.convertToEntity(certificateDto);
        em.merge(certificateToBeUpdated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(GiftCertificate certificate) {

        em.remove(certificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificate> read(long id) {

        return Optional.ofNullable(em.find(GiftCertificate.class, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificate> readCertificateByName(String certificateName) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(certificateRoot)
                .where(criteriaBuilder.equal(certificateRoot.get("name"), certificateName));
        TypedQuery<GiftCertificate> query = em.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findCertificateByParams(int page, int size, GiftCertificateDto certificateDto) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);

        List<Predicate> predicateList = new ArrayList<>();
        DaoQuery.applyIfNotNull(certificateDto.getName(),
                name -> predicateList.add(builder.like(certificateRoot.get("name"), "%" + name + "%")));
        DaoQuery.applyIfNotNull(certificateDto.getDescription(),
                description -> predicateList
                        .add(builder.like(certificateRoot.get("description"), "%" + description + "%")));

        predicateList.addAll(findCertificateByTags(certificateRoot, certificateDto, query, builder));
        query.select(certificateRoot).where(predicateList.toArray(new Predicate[0]));

        TypedQuery<GiftCertificate> q = em.createQuery(query);
        q.setFirstResult((page - 1) * size);
        q.setMaxResults(size);
        return q.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {

        return (long) em.createQuery("select count(c) from GiftCertificate c").getSingleResult();
    }

    private List<Predicate> findCertificateByTags(Root<GiftCertificate> cr, GiftCertificateDto dto,
            CriteriaQuery<GiftCertificate> cq, CriteriaBuilder cb) {

        List<Predicate> predicateList = new ArrayList<>();
        if (!dto.getTags().isEmpty()) {
            Set<String> tagNames = dto.getTags().stream().map(Tag::getName).collect(Collectors.toSet());
            Join<GiftCertificate, Tag> join = cr.join("tags", JoinType.LEFT);
            predicateList.add(join.get("name").in(tagNames));
            cq.groupBy(cr.get("id"));
            cq.having(cb.count(cr).in(dto.getTags().size()));
        }
        return predicateList;
    }
}
