package com.epam.esm.dao;

import java.util.List;

import com.epam.esm.entity.GiftCertificate;


public interface GiftCertificateDao {

    Long insert(GiftCertificate certificate);

    void update(GiftCertificate certificate);

    void delete(long id);

    GiftCertificate read(long id);

    List<GiftCertificate> findAll();

    //TODO to tag dao
    void insertCertificateTags(GiftCertificate certificate);

    List<Long> readCertificateTagsIdByCertificateId(long certificateId);

    void deleteCertificateTagsByCertificateId(long certificateId);

    List<GiftCertificate> findCertificateByParams(GiftCertificate certificate);
}
