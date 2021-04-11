package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.comparator.Direction;
import com.epam.esm.comparator.GiftCertificateSortService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoIdException;

/**
 * The Gift certificate service implementation
 */
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao certificateDao;

    private TagDao tagDao;

    private GiftCertificateSortService giftCertificateSortService;

    private GiftCertificateDtoConverter dtoConverter;

    @Autowired
    public void setCertificateDao(GiftCertificateDao certificateDao) {

        this.certificateDao = certificateDao;
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {

        this.tagDao = tagDao;
    }

    @Autowired
    public void setGiftCertificateSortService(GiftCertificateSortService giftCertificateSortService) {

        this.giftCertificateSortService = giftCertificateSortService;
    }

    @Autowired
    public void setDtoConverter(GiftCertificateDtoConverter dtoConverter) {

        this.dtoConverter = dtoConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Long add(GiftCertificateDto certificateDto) {

       // if (certificateDao.readCertificateByName(certificateDto.getName()))

        certificateDto.setCreateDate(LocalDateTime.now());
        certificateDto.setLastUpdateDate(LocalDateTime.now());
        Long certificateId = certificateDao.insert(certificateDto);
        certificateDto.setId(certificateId);

        Set<Tag> tagsWithId = tagDao
                .readTagsByNames(certificateDto.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        Map<Boolean, List<Tag>> tagMap = certificateDto.getTags().stream()
                .collect(Collectors.partitioningBy(tagsWithId::contains));

        Set<Tag> newTags=new HashSet<>(tagMap.get(false));
        insertTagsIfNotExist(newTags);
        tagsWithId.addAll(newTags);
        tagDao.bindCertificateTags(tagsWithId, certificateDto.getId());
        return certificateId;
    }

    /**
     * Insert tags to storage if they not already exists
     *
     * @param tags with names, id from which will be set
     */
    private void insertTagsIfNotExist(Set<Tag> tags) {

        tags.stream().filter(tag -> tag.getId() == null)
                .forEach(tag -> tag.setId(tagDao.insert(tag)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateDto read(long id) {

        GiftCertificate certificate = certificateDao.read(id).orElseThrow(()-> new NoCertificateException(id));
        Set<Long> tagsIds = tagDao.readCertificateTagsIdsByCertificateId(certificate.getId());
        Set<Tag> tags = tagsIds.stream().map(t -> tagDao.read(t)).collect(Collectors.toSet());
        return dtoConverter.convertToDto(certificate, tags);
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void update(GiftCertificateDto certificateDto) {

        Long certificateId = certificateDto.getId();
        if (certificateId == null) {
            throw new NoIdException();
        }
        if (certificateDao.read(certificateId) == null) {
            throw new NoCertificateException(certificateId);
        }

        certificateDto.setLastUpdateDate(LocalDateTime.now());
        certificateDao.update(certificateDto);
        if (!certificateDto.getTags().isEmpty()) {
            insertTagsIfNotExist(certificateDto.getTags());
            tagDao.unbindCertificateTags(certificateDto.getId());
            tagDao.bindCertificateTags(certificateDto.getTags(),
                    certificateDto.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        GiftCertificate certificate = certificateDao.read(id).orElseThrow(()-> new NoCertificateException(id));
        tagDao.unbindCertificateTags(certificate.getId());
        certificateDao.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findByParams(GiftCertificateDto certificateDto) {

        List<GiftCertificate> certificatesWithParams = certificateDao.findCertificateByParams(certificateDto);
        if (!certificateDto.getTags().isEmpty()) {
            List<GiftCertificate> certificatesWithTagParams = certificateDao
                    .findCertificateByTagName(certificateDto.getTag(0).getName());
            certificatesWithParams.retainAll(certificatesWithTagParams);
        }

        //read tags for certificate and convert it to dto
        return certificatesWithParams.stream()
                .map(c -> dtoConverter
                        .convertToDto(c, tagDao.readTagsByIds(
                                tagDao.readCertificateTagsIdsByCertificateId(c.getId()))))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> sortByParams(List<GiftCertificateDto> certificates,
            List<String> params, String direction) {

        Direction enumDirection = Direction.valueOf(direction.toUpperCase());
        return giftCertificateSortService.sort(certificates, params, enumDirection);
    }


}