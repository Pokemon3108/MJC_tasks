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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.TagDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

/**
 * Jpa implementation for access to database for work with tags
 */
@Repository
@Qualifier("tagJpaDao")
public class TagDaoImpl implements TagDao {

    private TagDtoConverter tagDtoConverter;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public TagDaoImpl(EntityManager em, TagDtoConverter tagDtoConverter) {

        this.em = em;
        this.tagDtoConverter = tagDtoConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long insert(TagDto dto) {

        Tag tag = tagDtoConverter.convertToEntity(dto);
        em.persist(tag);
        return tag.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TagDto> read(long id) {

        Tag tag = em.find(Tag.class, id);
        return Optional.ofNullable(tagDtoConverter.convertToDto(tag));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(TagDto dto) {

        Tag tag = tagDtoConverter.convertToEntity(dto);
        em.remove(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TagDto> readTagByName(String name) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));

        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return Optional.ofNullable(tagDtoConverter.convertToDto(query.getSingleResult()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCertificateTagsByTagId(long tagId) {

        Tag tag = em.find(Tag.class, tagId);
        tag.setCertificates(new HashSet<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindCertificateTags(Set<TagDto> tagSet, Long certificateId) {

        GiftCertificate certificate = em.find(GiftCertificate.class, certificateId);
        certificate.setTags(tagDtoConverter.convertToEntities(tagSet));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Long> readCertificateTagsIdsByCertificateId(long certificateId) {

        GiftCertificate certificate = em.find(GiftCertificate.class, certificateId);
        return certificate.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindCertificateTags(GiftCertificateDto certificateDto) {

        GiftCertificate certificate = em.find(GiftCertificate.class, certificateDto.getId());
        certificate.setTags(new HashSet<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<TagDto> readTagsByNames(Set<String> tagNames) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root).where(root.get("name").in(tagNames));
        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return tagDtoConverter.convertToDtos(query.getResultStream().collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<TagDto> readTagsByIds(Set<Long> ids) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root).where(root.get("id").in(ids));
        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return tagDtoConverter.convertToDtos(query.getResultStream().collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TagDto> readTheMostPopularTag(UserDto user) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<User> root = criteriaQuery.from(User.class);

        Join<User, Order> orderJoin = root.join("orders", JoinType.LEFT);
        Join<Order, GiftCertificate> certificateJoin = orderJoin.join("certificate", JoinType.LEFT);
        Join<GiftCertificate, Tag> tagJoin = certificateJoin.join("tags", JoinType.LEFT);

        criteriaQuery.select(tagJoin).where(root.get("id").in(user.getId())).groupBy(tagJoin.get("id"))
                .orderBy(criteriaBuilder.desc(criteriaBuilder.count(tagJoin)));
        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return query.getResultStream().findFirst().map(t -> tagDtoConverter.convertToDto(t));
    }
}
