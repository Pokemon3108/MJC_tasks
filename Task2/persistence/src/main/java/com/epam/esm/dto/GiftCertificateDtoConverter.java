package com.epam.esm.dto;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

@Component
public class GiftCertificateDtoConverter {

    public GiftCertificateDto convertToDto(GiftCertificate certificate, Set<Tag> tagSet) {

        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setDescription(certificate.getDescription());
        dto.setName(certificate.getName());
        dto.setCreateDate(certificate.getCreateDate());
        dto.setLastUpdateDate(certificate.getLastUpdateDate());
        dto.setDuration(certificate.getDuration());
        dto.setPrice(certificate.getPrice());
        tagSet.forEach(dto::addTag);
        return dto;
    }

    public GiftCertificate convertToEntity(GiftCertificateDto dto) {

        GiftCertificate certificate = new GiftCertificate();
        certificate.setDescription(dto.getDescription());
        certificate.setName(dto.getName());
        certificate.setCreateDate(dto.getCreateDate());
        certificate.setLastUpdateDate(dto.getLastUpdateDate());
        certificate.setDuration(dto.getDuration());
        certificate.setPrice(dto.getPrice());
        return certificate;
    }
}
