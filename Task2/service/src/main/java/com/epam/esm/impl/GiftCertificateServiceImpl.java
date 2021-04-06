package com.epam.esm.impl;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Gift certificate service implementation
 */
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDao certificateDao;

    @Autowired
    private TagService tagService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Long add(GiftCertificate certificate) {
        certificate.setCreateDate(getCurrentTime());
        certificate.setLastUpdateDate(getCurrentTime());
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
        if (certificate != null) {
            List<Long> tagsId = certificateDao.readCertificateTagsIdByCertificateId(id);
            List<Tag> tags = tagsId.stream().map(tagId -> tagService.readTagById(tagId)).collect(Collectors.toList());
            certificate.setTags(tags);
        } else throw new NoCertificateException(id);

        return certificate;
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void update(GiftCertificate certificate) {
        Long certificateId = certificate.getId();
        if (certificateId == null) throw new NoIdException();
        if (certificateDao.read(certificateId) == null) throw new NoCertificateException(certificateId);

        certificate.setLastUpdateDate(getCurrentTime());
        certificateDao.update(certificate);
        if (certificate.getTags() != null) {
            setCertificateTagsId(certificate.getTags());
            certificateDao.deleteCertificateTags(certificate);
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
        if (certificate == null) throw new NoCertificateException(id);
        certificateDao.deleteCertificateTags(certificate);
        certificateDao.delete(id);
    }

    /**
     * Set id for certificate's tags
     *
     * @param tags with names, id from which will be set
     */
    private void setCertificateTagsId(List<Tag> tags) {
        //set id for tags that already exist
        tags.forEach(tag -> tag.setId(tagService.readTagByName(tag.getName()).getId()));

        //insert tags and set id for them if they not exist yet
        tags.stream().filter(tag -> tag.getId() == null)
                .forEach(tag -> tag.setId(tagService.insert(tag)));
    }

    /**
     * @return current time
     */
    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}