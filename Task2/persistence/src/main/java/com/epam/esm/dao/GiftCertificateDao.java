package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;


public interface GiftCertificateDao {
    Long insert(GiftCertificate certificate);

    void update(GiftCertificate certificate);

    void delete(long id);

    GiftCertificate read(long id);

    List<GiftCertificate> findAll();

    void insertCertificateTags(GiftCertificate certificate);

    List<Long> readCertificateTagsIdByCertificateId(long certificateId);

    void deleteCertificateTags(GiftCertificate certificate);
}
