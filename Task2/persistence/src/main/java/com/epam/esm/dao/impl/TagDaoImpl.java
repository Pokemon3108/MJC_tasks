package com.epam.esm.dao.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.TagDtoConverter;
import com.epam.esm.entity.Tag;

/**
 * Jpa implementation for access to database for work with tags
 */
@Repository
public class TagDaoImpl implements TagDao {

    private TagDtoConverter tagDtoConverter;

    private EntityManager em;

    @Autowired
    public TagDaoImpl(TagDtoConverter tagDtoConverter) {

        this.tagDtoConverter = tagDtoConverter;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {

        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagDto insert(TagDto dto) {

        Tag tag = tagDtoConverter.convertToEntity(dto);
        em.persist(tag);
        return tagDtoConverter.convertToDto(tag);
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

        Tag tag = em.find(Tag.class, dto.getId());
        tag.getCertificates().forEach(c -> c.getTags().remove(tag));
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
        return query.getResultList().stream().findFirst().map(t -> tagDtoConverter.convertToDto(t));
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
    public Optional<TagDto> readTheMostPopularTagOfRichestUser() {

        Query query = em.createNativeQuery(
                "SELECT tag.name, tag.id FROM tag JOIN gift_certificate_tag AS gct ON tag.id=gct.tag_id "
                        + "JOIN gift_certificate AS gc ON gc.id=gct.certificate_id "
                        + "JOIN ordr ON ordr.certificate_id=gc.id "
                        + "JOIN usr ON usr.id=ordr.user_id WHERE usr.id = "
                        + "(SELECT usr.id FROM usr join ordr on usr.id=ordr.user_id GROUP BY usr.id "
                        + "ORDER BY SUM(cost) DESC LIMIT 1) "
                        + "GROUP BY tag.name, tag.id ORDER BY COUNT(tag.name) DESC LIMIT 1"
                ,
                Tag.class);

        Tag tag = (Tag) query.getSingleResult();
        return Optional.ofNullable(tagDtoConverter.convertToDto(tag));

    }
}
