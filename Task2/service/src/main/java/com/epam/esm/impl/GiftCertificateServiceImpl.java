package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.NoIdException;
import com.epam.esm.exception.NoPageException;
import com.epam.esm.exception.certificate.DuplicateCertificateException;
import com.epam.esm.exception.certificate.NoCertificateException;
import com.epam.esm.utils.BeanNullProperty;

/**
 * The Gift certificate service implementation
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao certificateDao;

    private TagDao tagDao;

    private TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, TagDao tagDao, TagService tagService) {

        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
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

        Set<TagDto> tags = tagService.bindTagsWithIds(certificateDto.getTags());
        certificateDto.setTags(tags);
        return certificateDao.insert(certificateDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateDto read(long id) {

        return certificateDao.read(id)
                .orElseThrow(() -> new NoCertificateException(id));
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

        GiftCertificateDto certificateWithId = certificateDao.read(certificateId)
                .orElseThrow(() -> new NoCertificateException(certificateId));

        Optional<GiftCertificateDto> certificateWithName = certificateDao
                .readCertificateByName(certificateDto.getName());
        if (certificateWithName.isPresent() && !certificateWithName.get().getId().equals(certificateDto.getId())) {
            throw new DuplicateCertificateException(certificateDto.getName());
        }

        certificateDto.setLastUpdateDate(LocalDateTime.now());

        copyProperties(certificateDto, certificateWithId);
        certificateDao.update(certificateWithId);

        if (!certificateDto.getTags().isEmpty()) {
            tagDao.unbindCertificateTags(certificateDto);

            long namedTagsSize = certificateDto.getTags().stream().filter(t -> t.getName() != null).count();
            if (namedTagsSize != 0) {
                Set<TagDto> tagsWithId = tagService.bindTagsWithIds(certificateDto.getTags());

                tagDao.bindCertificateTags(tagsWithId,
                        certificateDto.getId());
            }
        }
    }

    /**
     * Copy properties from dto to certificate
     *
     * @param src    the source object
     * @param target the target object
     */
    private void copyProperties(GiftCertificateDto src, GiftCertificateDto target) {

        Set<TagDto> tags = src.getTags();
        BeanNullProperty.copyNonNullProperties(src, target);
        if (!tags.isEmpty()) {
            src.setTags(tags);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        GiftCertificateDto certificate = certificateDao.read(id).orElseThrow(() -> new NoCertificateException(id));
        tagDao.unbindCertificateTags(certificate);
        certificateDao.delete(certificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findByParams(int page, int size, GiftCertificateDto certificateDto) {

        if (certificateDao.getAllCount() < (page - 1) * size) {
            throw new NoPageException(page, size);
        }
        List<GiftCertificateDto> certificatesWithParams = certificateDao
                .findCertificateByParams(page, size, certificateDto);
        return new ArrayList<>(certificatesWithParams);
    }

}