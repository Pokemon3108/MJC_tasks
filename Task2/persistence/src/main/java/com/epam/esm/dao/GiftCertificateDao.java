package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateDao extends Dao<GiftCertificate> {
    void insertCertificateTag(GiftCertificate certificate);
}
