package com.epam.esm.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.query.DaoQuery;
import com.epam.esm.dto.Direction;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SortParamsDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

/**
 * Jpa implementation of certificate dao
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private GiftCertificateDtoConverter converter;

    private EntityManager em;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateDtoConverter converter) {

        this.converter = converter;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {

        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long insert(GiftCertificateDto certificateDto) {

        GiftCertificate certificate = converter.convertToEntity(certificateDto);

        Set<Tag> tags = certificate.getTags()
                .stream()
                .map(t -> t.getId() != null ? em.merge(t) : t)
                .collect(Collectors.toSet());
        certificate.setTags(tags);

        em.persist(certificate);
        return certificate.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(GiftCertificateDto certificateDto) {

        GiftCertificate certificateToBeUpdated = converter.convertToEntity(certificateDto);
        GiftCertificate certificateFromDb = em.find(GiftCertificate.class, certificateDto.getId());
        certificateToBeUpdated.setOrders(certificateFromDb.getOrders());
        em.merge(certificateToBeUpdated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(GiftCertificateDto certificateDto) {

        GiftCertificate certificateToBeDeleted = em.find(GiftCertificate.class, certificateDto.getId());
        certificateToBeDeleted.setTags(new HashSet<>());
        em.remove(certificateToBeDeleted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificateDto> read(long id) {

        return Optional.ofNullable(converter.convertToDto(em.find(GiftCertificate.class, id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificateDto> readCertificateByName(String certificateName) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(certificateRoot)
                .where(criteriaBuilder.equal(certificateRoot.get("name"), certificateName));
        TypedQuery<GiftCertificate> query = em.createQuery(criteriaQuery);
        return query.getResultStream().findFirst().map(c -> converter.convertToDto(c));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findCertificateByParams(int page, int size, GiftCertificateDto certificateDto,
            SortParamsDto sortParamsDto) {

        CriteriaQuery<GiftCertificate> criteriaQuery = buildFindByParamsQuery(certificateDto, sortParamsDto);

        TypedQuery<GiftCertificate> q = em.createQuery(criteriaQuery);
        q.setFirstResult((page - 1) * size);
        q.setMaxResults(size);
        return new ArrayList<>(converter.convertToDtos(new LinkedHashSet<>(q.getResultList())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getAllCount() {

        return (long) em.createQuery("select count(c) from GiftCertificate c").getSingleResult();
    }

    @Override
    public long countFoundCertificates(GiftCertificateDto dto) {

        CriteriaQuery<GiftCertificate> criteriaQuery = buildFindByParamsQuery(dto, null);
        TypedQuery<GiftCertificate> q = em.createQuery(criteriaQuery);
        return q.getResultStream().count();
    }

    private List<Predicate> getPredicatesForSearchByTagNames(Root<GiftCertificate> cr, GiftCertificateDto dto,
            CriteriaQuery<GiftCertificate> cq, CriteriaBuilder cb) {

        List<Predicate> predicateList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dto.getTags())) {
            Set<String> tagNames = dto.getTags().stream().map(TagDto::getName).collect(Collectors.toSet());
            Join<GiftCertificate, Tag> join = cr.join("tags", JoinType.LEFT);
            predicateList.add(join.get("name").in(tagNames));
            cq.groupBy(cr.get("id"));
            cq.having(cb.count(cr).in(dto.getTags().size()));
        }
        return predicateList;
    }

    private CriteriaQuery<GiftCertificate> buildFindByParamsQuery(GiftCertificateDto certificateDto,
            SortParamsDto sortParams) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);

        List<Predicate> predicateList = new ArrayList<>();
        DaoQuery.applyIfNotNull(certificateDto.getName(),
                name -> predicateList.add(builder.like(certificateRoot.get("name"), "%" + name + "%")));
        DaoQuery.applyIfNotNull(certificateDto.getDescription(),
                description -> predicateList
                        .add(builder.like(certificateRoot.get("description"), "%" + description + "%")));

        if (sortParams != null && !sortParams.getSortParams().isEmpty()) {
            Function<Path<?>, Order> orderSortBuilder = sortParams.getDirection().equals(Direction.ASC)
                    ? builder::asc
                    : builder::desc;
            sortParams.getSortParams()
                    .forEach(param -> query.orderBy(orderSortBuilder.apply(certificateRoot.get(param))));
        }

        predicateList.addAll(getPredicatesForSearchByTagNames(certificateRoot, certificateDto, query, builder));
        query.select(certificateRoot).where(predicateList.toArray(new Predicate[0]));
        return query;
    }
}
