package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;


public interface GiftCertificateDao {
    Long insert(GiftCertificate certificate);

    void update(long id);

    void delete(GiftCertificate certificate);

    GiftCertificate read(long id);

    List<GiftCertificate> findAll();

    void insertCertificateTag(GiftCertificate certificate);
}
