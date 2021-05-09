package com.epam.esm.impl;

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
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SortParamsDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.MaxSizeLimitException;
import com.epam.esm.exception.NoIdException;
import com.epam.esm.exception.NoPageException;
import com.epam.esm.exception.certificate.CertificateIsOrderedException;
import com.epam.esm.exception.certificate.DuplicateCertificateException;
import com.epam.esm.exception.certificate.NoCertificateException;

/**
 * The Gift certificate service implementation
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao certificateDao;

    private TagService tagService;

    private OrderDao orderDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, TagService tagService, OrderDao orderDao) {

        this.certificateDao = certificateDao;
        this.tagService = tagService;
        this.orderDao = orderDao;
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto certificateDto) {

        if (certificateDao.readCertificateByName(certificateDto.getName()).isPresent()) {
            throw new DuplicateCertificateException(certificateDto.getName());
        }

        Set<TagDto> tags = tagService.bindTagsWithIds(certificateDto.getTags());
        certificateDto.setTags(tags);
        return read(certificateDao.insert(certificateDto));
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

        copyProperties(certificateDto, certificateWithId);
        if (!certificateDto.getTags().isEmpty()) {
            Set<TagDto> tags = tagService.bindTagsWithIds(certificateDto.getTags());
            certificateWithId.setTags(tags);
        }
        certificateDao.update(certificateWithId);
    }

    /**
     * Copy properties from dto to certificate
     *
     * @param src    the source object
     * @param target the target object
     */
    private void copyProperties(GiftCertificateDto src, GiftCertificateDto target) {

        if (src.getName() != null) {
            target.setName(src.getName());
        }
        if (src.getDescription() != null) {
            target.setDescription(src.getDescription());
        }
        if (src.getDuration() != null) {
            target.setDuration(src.getDuration());
        }
        if (src.getPrice() != null) {
            target.setPrice(src.getPrice());
        }
        if (!src.getTags().isEmpty()) {
            target.setTags(src.getTags());
        }
        target.setId(src.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        GiftCertificateDto certificate = certificateDao.read(id).orElseThrow(() -> new NoCertificateException(id));
        if (orderDao.anyOrderHasCertificate(certificate)) {
            throw new CertificateIsOrderedException(certificate.getId());
        }
        certificateDao.delete(certificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findByParams(int page, int size, GiftCertificateDto certificateDto,
            SortParamsDto sortParams) {

        final int maxSize = 100;
        if (size > maxSize || size < 0) {
            throw new MaxSizeLimitException(maxSize);
        }
        if (page < 0 || certificateDao.getAllCount() < (page - 1) * size) {
            throw new NoPageException(page, size);
        }
        List<GiftCertificateDto> certificatesWithParams = certificateDao
                .findCertificateByParams(page, size, certificateDto, sortParams);
        return new ArrayList<>(certificatesWithParams);
    }

    @Override
    public long countFoundByParamsCertificates(GiftCertificateDto dto) {

        return certificateDao.countFoundCertificates(dto);
    }

}