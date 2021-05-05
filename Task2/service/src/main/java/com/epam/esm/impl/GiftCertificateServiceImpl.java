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
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.NoIdException;
import com.epam.esm.exception.NoPageException;
import com.epam.esm.exception.certificate.DuplicateCertificateException;
import com.epam.esm.exception.certificate.NoCertificateException;

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

        copyProperties(certificateDto, certificateWithId);
        Set<TagDto> tags = tagService.bindTagsWithIds(certificateDto.getTags());
        certificateWithId.setTags(tags);
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
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        GiftCertificateDto certificate = certificateDao.read(id).orElseThrow(() -> new NoCertificateException(id));
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