package com.epam.esm.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.comparator.ComparatorService;
import com.epam.esm.dao.GiftCertificateDao;
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
    private TagService tagService;

    @Autowired
    private ComparatorService comparatorService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Long add(GiftCertificate certificate) {

        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        Long certificateId = certificateDao.insert(certificate);
        certificate.setId(certificateId);
        setCertificateTagsId(certificate.getTags());
        certificateDao.insertCertificateTags(certificate);
        return certificateId;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate read(long id) {

        GiftCertificate certificate = certificateDao.read(id);
        if (certificate == null) {
            throw new NoCertificateException(id);
        }
        return certificate;
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void update(GiftCertificate certificate) {

        Long certificateId = certificate.getId();
        if (certificateId == null) {
            throw new NoIdException();
        }
        if (certificateDao.read(certificateId) == null) {
            throw new NoCertificateException(certificateId);
        }

        certificate.setLastUpdateDate(LocalDateTime.now());
        certificateDao.update(certificate);
        if (!certificate.getTags().isEmpty()) {
            setCertificateTagsId(certificate.getTags());
            certificateDao.deleteCertificateTagsByCertificateId(certificate.getId());
            certificateDao.insertCertificateTags(certificate);
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
        certificateDao.deleteCertificateTagsByCertificateId(certificate.getId());
        certificateDao.delete(id);
    }

    @Override
    public List<GiftCertificate> findByParams(GiftCertificate certificate) {

        return certificateDao.findCertificateByParams(certificate);
    }

    @Override
    public List<GiftCertificate> sortByParams(List<GiftCertificate> certificates,
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