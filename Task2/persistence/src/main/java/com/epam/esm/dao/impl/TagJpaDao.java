package com.epam.esm.dao.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

@Repository
@Qualifier("tagJpaDao")
public class TagJpaDao implements TagDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public TagJpaDao(EntityManager em) {

        this.em = em;
    }

    @Override
    public Long insert(Tag tag) {

        em.persist(tag);
        return tag.getId();
    }

    @Override
    public Optional<Tag> read(long id) {

        Tag tag = em.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public void delete(Tag tag) {

        em.remove(tag);
    }

    @Override
    public Optional<Tag> readTagByName(String name) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));

        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void deleteCertificateTagsByTagId(long tagId) {

        Tag tag = em.find(Tag.class, tagId);
        tag.setCertificates(new HashSet<>());
    }

    @Override
    public void bindCertificateTags(Set<Tag> tagSet, Long certificateId) {

        GiftCertificate certificate = em.find(GiftCertificate.class, certificateId);
        certificate.setTags(tagSet);
    }

    @Override
    public Set<Long> readCertificateTagsIdsByCertificateId(long certificateId) {

        GiftCertificate certificate = em.find(GiftCertificate.class, certificateId);
        return certificate.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
    }

    @Override
    public void unbindCertificateTags(GiftCertificate certificate) {

        certificate.setTags(new HashSet<>());
    }

    @Override
    public Set<Tag> readTagsByNames(Set<String> tagNames) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root).where(root.get("name").in(tagNames));
        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return query.getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Set<Tag> readTagsByIds(Set<Long> ids) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root).where(root.get("id").in(ids));
        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return query.getResultStream().collect(Collectors.toSet());
    }
}
