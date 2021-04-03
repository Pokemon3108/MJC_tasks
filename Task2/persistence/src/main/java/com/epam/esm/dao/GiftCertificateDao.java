package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;


public interface GiftCertificateDao extends Dao<GiftCertificate> {
    void insertCertificateTag(GiftCertificate certificate);
}
