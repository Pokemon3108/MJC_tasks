package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repos.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GiftCertificateService {

    @Autowired
    private GiftCertificateRepository certificateRepository;

    public Long add(GiftCertificate certificate) {
        certificate.setCreateDate(getCurrentIsoTime());
        certificate.setLastUpdateDate(getCurrentIsoTime());
        return certificateRepository.add(certificate);
    }

    private LocalDateTime getCurrentIsoTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String isoTimeNow = ZonedDateTime.now(ZoneOffset.UTC).format(formatter);
        return LocalDateTime.parse(isoTimeNow);
    }
}
