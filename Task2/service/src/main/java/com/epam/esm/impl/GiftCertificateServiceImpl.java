package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateCertificateException;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoIdException;
import com.epam.esm.exception.NoPageException;
import com.epam.esm.utils.BeanNullProperty;

/**
 * The Gift certificate service implementation
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao certificateDao;

    private TagDao tagDao;

    private GiftCertificateDtoConverter dtoConverter;

    private TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, TagDao tagDao,
            GiftCertificateDtoConverter dtoConverter, TagService tagService) {

        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.dtoConverter = dtoConverter;
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

        if (!certificateDto.getTags().isEmpty()) {
            Set<Tag> tagsWithId = tagService.setTagsId(certificateDto.getTags());
            tagDao.bindCertificateTags(tagsWithId, certificateDto.getId());
        }
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

        GiftCertificate certificateWithId = certificateDao.read(certificateId)
                .orElseThrow(() -> new NoCertificateException(certificateId));

        Optional<GiftCertificate> certificateWithName = certificateDao.readCertificateByName(certificateDto.getName());
        if (certificateWithName.isPresent() && !certificateWithName.get().getId().equals(certificateDto.getId())) {
            throw new DuplicateCertificateException(certificateDto.getName());
        }

        certificateDto.setLastUpdateDate(LocalDateTime.now());

        copyProperties(certificateDto, certificateWithId);
        certificateDao.update(certificateDto);

        if (!certificateDto.getTags().isEmpty()) {
            tagDao.unbindCertificateTags(dtoConverter.convertToEntity(certificateDto));

            long namedTagsSize = certificateDto.getTags().stream().filter(t -> t.getName() != null).count();
            if (namedTagsSize != 0) {
                Set<Tag> tagsWithId = tagService.setTagsId(certificateDto.getTags());

                tagDao.bindCertificateTags(tagsWithId,
                        certificateDto.getId());
            }
        }
    }

    /**
     * Copy properties from dto to certificate
     *
     * @param dto         the source object
     * @param certificate the target object
     */
    private void copyProperties(GiftCertificateDto dto, GiftCertificate certificate) {

        Set<Tag> tags = dto.getTags();
        BeanNullProperty.copyNonNullProperties(certificate, dto);
        if (!tags.isEmpty()) {
            dto.setTags(tags);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        GiftCertificate certificate = certificateDao.read(id).orElseThrow(() -> new NoCertificateException(id));
        tagDao.unbindCertificateTags(certificate);
        certificateDao.delete(certificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findByParams(int page, int size, GiftCertificateDto certificateDto) {

        if (certificateDao.size() < (page - 1) * size) {
            throw new NoPageException(page, size);
        }
        List<GiftCertificate> certificatesWithParams = certificateDao
                .findCertificateByParams(page, size, certificateDto);
        return certificatesWithParams.stream().map(c -> dtoConverter.convertToDto(c, c.getTags()))
                .collect(Collectors.toList());
    }

}