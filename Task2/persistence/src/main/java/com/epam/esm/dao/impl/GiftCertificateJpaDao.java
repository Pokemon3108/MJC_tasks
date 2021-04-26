package com.epam.esm.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;

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

    @Override
    public Long insert(GiftCertificateDto certificateDto) {

        GiftCertificate certificate = converter.convertToEntity(certificateDto);
        em.persist(certificate);
        return certificate.getId();
    }

    @Override
    public void update(GiftCertificateDto certificateDto) {

        GiftCertificate certificateToBeUpdated = converter.convertToEntity(certificateDto);
        em.merge(certificateToBeUpdated);
    }

    @Override
    public void delete(GiftCertificate certificate) {

        em.remove(certificate);
    }

    @Override
    public Optional<GiftCertificate> read(long id) {

        return Optional.ofNullable(em.find(GiftCertificate.class, id));
    }

    @Override
    public Optional<GiftCertificate> readCertificateByName(String certificateName) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), certificateName));
        TypedQuery<GiftCertificate> query = em.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<GiftCertificate> findCertificateByParams(GiftCertificateDto certificateDto) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);

        CriteriaBuilder.Coalesce<GiftCertificate> coalesceExp = builder.coalesce();
        coalesceExp.value(root.get("name"));
        coalesceExp.value(root.get("description"));
        query.select(coalesceExp);

        TypedQuery<GiftCertificate> q = em.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<GiftCertificate> findCertificateByTagName(String tagName) {

        return null;
    }
}
