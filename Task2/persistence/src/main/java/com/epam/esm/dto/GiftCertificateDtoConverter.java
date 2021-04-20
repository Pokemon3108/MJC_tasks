package com.epam.esm.dto;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;


/**
 * The type Gift certificate dto converter for converting from entity to dto and vice versa
 */
@Component
public class GiftCertificateDtoConverter {

    /**
     * Convert to dto gift certificate dto.
     *
     * @param certificate the certificate entity
     * @param tagSet      set of certificate tags
     * @return the dto object, that created from certificate and tagSet
     */
    public GiftCertificateDto convertToDto(GiftCertificate certificate, Set<Tag> tagSet) {

        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setDescription(certificate.getDescription());
        dto.setName(certificate.getName());
        dto.setCreateDate(certificate.getCreateDate());
        dto.setLastUpdateDate(certificate.getLastUpdateDate());
        dto.setDuration(certificate.getDuration());
        dto.setPrice(certificate.getPrice());
        dto.setId(certificate.getId());
        tagSet.forEach(dto::addTag);
        return dto;
    }

    /**
     * Convert dto to entity
     *
     * @param dto
     * @return the gift certificate, that created from {@code dto}
     */
    public GiftCertificate convertToEntity(GiftCertificateDto dto) {

        GiftCertificate certificate = new GiftCertificate();
        certificate.setDescription(dto.getDescription());
        certificate.setName(dto.getName());
        certificate.setCreateDate(dto.getCreateDate());
        certificate.setLastUpdateDate(dto.getLastUpdateDate());
        certificate.setDuration(dto.getDuration());
        certificate.setPrice(dto.getPrice());
        certificate.setId(dto.getId());
        return certificate;
    }
}
