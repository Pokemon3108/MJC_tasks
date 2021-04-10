package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.comparator.ComparatorService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dtoconverter.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoIdException;

/**
 * The Gift certificate service implementation
 */
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDao certificateDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TagService tagService;

    @Autowired
    private ComparatorService comparatorService;

    @Autowired
    private GiftCertificateDtoConverter dtoConverter;

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Long add(GiftCertificateDto certificateDto) {

        certificateDto.setCreateDate(LocalDateTime.now());
        certificateDto.setLastUpdateDate(LocalDateTime.now());
        Long certificateId = certificateDao.insert(certificateDto);
        certificateDto.setId(certificateId);
        setCertificateTagsId(certificateDto.getTags());
        tagDao.insertCertificateTags(certificateDto);
        return certificateId;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateDto read(long id) {

        GiftCertificate certificate = certificateDao.read(id);
        Set<Tag> tags = new HashSet<>(tagDao.readCertificateTagsIdByCertificateId(certificate.getId()));
        if (certificate == null) {
            throw new NoCertificateException(id);
        }
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
            setCertificateTagsId(certificateDto.getTags());
            tagDao.deleteCertificateTagsByCertificateId(certificateDto.getId());
            tagDao.insertCertificateTags(certificateDto);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        GiftCertificate certificate = certificateDao.read(id);
        if (certificate == null) {
            throw new NoCertificateException(id);
        }
        tagDao.deleteCertificateTagsByCertificateId(certificate.getId());
        certificateDao.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findByParams(GiftCertificateDto certificateDto) {

        List<GiftCertificate> certificatesWithParams = certificateDao.findCertificateByParams(certificateDto);

        if (certificateDto.hasTags()) {
            List<GiftCertificate> certificatesWithTagParams = certificateDao
                    .findCertificateByTagName(certificateDto.getTag(0).getName());
            certificatesWithParams.retainAll(certificatesWithTagParams);
        }

        //read tags for certificate and convert it to dto
        return certificatesWithParams.stream()
                .map(c -> dtoConverter
                        .convertToDto(c, new HashSet<>(tagDao.readCertificateTagsIdByCertificateId(c.getId()))))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> sortByParams(List<GiftCertificateDto> certificates,
            List<String> params, String direction) {

        return comparatorService.sort(certificates, params, direction);
    }

    /**
     * Set id for certificate's tags
     *
     * @param tags with names, id from which will be set
     */
    private void setCertificateTagsId(Set<Tag> tags) {
        //set id for tags that already exist
        tags.forEach(tag -> tag.setId(tagService.readTagByName(tag.getName()).getId()));

        //insert tags and set id for them if they not exist yet
        tags.stream().filter(tag -> tag.getId() == null)
                .forEach(tag -> tag.setId(tagService.create(tag)));
    }

}