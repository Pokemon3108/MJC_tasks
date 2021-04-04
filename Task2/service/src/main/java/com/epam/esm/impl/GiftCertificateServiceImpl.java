package com.epam.esm.impl;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The Gift certificate service.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDao certificateDao;

    @Autowired
    private TagServiceImpl tagService;

    /**
     * Insert certificate to storage
     *
     * @param certificate
     * @return the id of inserted certificate
     */
    public Long add(GiftCertificate certificate) {
        certificate.setCreateDate(getCurrentIsoTime());
        certificate.setLastUpdateDate(getCurrentIsoTime());
        Long certificateId = certificateDao.insert(certificate);
        certificate.setId(certificateId);
        setCertificateTagsId(certificate.getTags());
        certificateDao.insertCertificateTag(certificate);
        return certificateId;
    }

    /**
     * Set id for certificate's tags
     *
     * @param tags
     */
    private void setCertificateTagsId(List<Tag> tags) {
        tags.forEach(tag -> tag.setId(tagService.findTagByName(tag).getId()));
        tags.stream().filter(tag -> tag.getId() == null)
                .forEach(tag -> tag.setId(tagService.insert(tag)));
    }

    /**
     * @return current time
     */
    private LocalDateTime getCurrentIsoTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String isoTimeNow = ZonedDateTime.now(ZoneOffset.UTC).format(formatter);
        return LocalDateTime.parse(isoTimeNow);
    }
}
