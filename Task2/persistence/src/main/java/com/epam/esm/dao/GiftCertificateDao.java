package com.epam.esm.dao;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;


public interface GiftCertificateDao {

    Long insert(GiftCertificateDto certificateDto);

    void update(GiftCertificateDto certificateDto);

    void delete(long id);

    GiftCertificate read(long id);

    List<GiftCertificate> findCertificateByParams(GiftCertificateDto certificateDto);

    List<GiftCertificate> findCertificateByTagName(String tagName);
}
