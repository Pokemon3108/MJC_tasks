package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.comparator.Direction;
import com.epam.esm.comparator.GiftCertificateSortService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateCertificateException;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoIdException;

/**
 * The Gift certificate service implementation
 */
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao certificateDao;

    private TagDao tagDao;

    private GiftCertificateDtoConverter dtoConverter;

    private TagService tagService;

    @Autowired
    public void setCertificateDao(GiftCertificateDao certificateDao) {

        this.certificateDao = certificateDao;
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {

        this.tagDao = tagDao;
    }

    @Autowired
    public void setDtoConverter(GiftCertificateDtoConverter dtoConverter) {

        this.dtoConverter = dtoConverter;
    }

    @Autowired
    public void setTagService(TagService tagService) {

        this.tagService = tagService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Long add(GiftCertificateDto certificateDto) {

        if (certificateDao.readCertificateByName(certificateDto.getName()).isPresent()) {
            throw new DuplicateCertificateException(certificateDto.getName());
        }

        certificateDto.setCreateDate(LocalDateTime.now());
        certificateDto.setLastUpdateDate(LocalDateTime.now());
        Long certificateId = certificateDao.insert(certificateDto);
        certificateDto.setId(certificateId);

        Set<Tag> tagsWithId = tagService.setTagsId(certificateDto.getTags());
        tagDao.bindCertificateTags(tagsWithId, certificateDto.getId());
        return certificateId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateDto read(long id) {

        GiftCertificate certificate = certificateDao.read(id).orElseThrow(() -> new NoCertificateException(id));
        Set<Long> tagsIds = tagDao.readCertificateTagsIdsByCertificateId(certificate.getId());
        Set<Tag> tags = tagsIds.stream().map(t -> tagDao.read(t).get()).collect(Collectors.toSet());
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
        if (!certificateDao.read(certificateId).isPresent()) {
            throw new NoCertificateException(certificateId);
        }

        certificateDto.setLastUpdateDate(LocalDateTime.now());
        certificateDao.update(certificateDto);
        if (!certificateDto.getTags().isEmpty()) {
            Set<Tag> tagsWithId = tagService.setTagsId(certificateDto.getTags());
            tagDao.unbindCertificateTags(certificateDto.getId());
            tagDao.bindCertificateTags(tagsWithId,
                    certificateDto.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        GiftCertificate certificate = certificateDao.read(id).orElseThrow(() -> new NoCertificateException(id));
        tagDao.unbindCertificateTags(certificate.getId());
        certificateDao.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findByParams(GiftCertificateDto certificateDto) {

        List<GiftCertificate> certificatesWithParams = certificateDao.findCertificateByParams(certificateDto);
        if (certificateDto.getTag(0).getName() != null) {
            List<GiftCertificate> certificatesWithTagParams = certificateDao
                    .findCertificateByTagName(certificateDto.getTag(0).getName());
            certificatesWithParams.retainAll(certificatesWithTagParams);
        }

        //read tags for certificate and convert it to dto
        return certificatesWithParams.stream()
                .map(c -> dtoConverter
                        .convertToDto(c,
                                tagDao.readTagsByIds(tagDao.readCertificateTagsIdsByCertificateId(c.getId()))))
                .collect(Collectors.toList());
    }

}