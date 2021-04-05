package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Service;

@Service
public interface GiftCertificateService {
    Long add(GiftCertificate certificate);

    GiftCertificate read(long id);
}
