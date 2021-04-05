package com.epam.esm.impl;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Gift certificate service.
 */
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDao certificateDao;

    @Autowired
    private TagService tagService;

    /**
     * Insert certificate to storage
     *
     * @param certificate
     * @return the id of inserted certificate
     */
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
     * Read gift certificate by its id
     *
     * @param id certificate id
     * @return certificate
     */
    @Override
    public GiftCertificate read(long id) {
        GiftCertificate certificate = certificateDao.read(id);
        List<Long> tagsId = certificateDao.readCertificateTagsIdByCertificateId(id);
        List<Tag> tags = tagsId.stream().map(tagId -> tagService.findTagById(tagId)).collect(Collectors.toList());
        certificate.setTags(tags);
        return certificate;
    }

    @Override
    public void update(GiftCertificate certificate) {
        certificate.setLastUpdateDate(getCurrentTime());
        certificateDao.update(certificate);
        if (certificate.getTags()!=null) {
            setCertificateTagsId(certificate.getTags());
        }
    }

    /**
     * Set id for certificate's tags
     *
     * @param tags with names, id from which will be set
     */
    private void setCertificateTagsId(List<Tag> tags) {
        //set id for tags that already exist
        tags.forEach(tag -> tag.setId(tagService.findTagByName(tag).getId()));

        //insert tags and set id for them if they not exist yet
        tags.stream().filter(tag -> tag.getId() == null)
                .forEach(tag -> tag.setId(tagService.insert(tag)));
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
