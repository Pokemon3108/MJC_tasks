package com.epam.esm.dto.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;


/**
 * The type Gift certificate dto converter for converting from entity to dto and vice versa
 */
@Component
public class GiftCertificateDtoConverter {

    /**
     * Convert to dto gift certificate dto.
     *
     * @param certificate the certificate entity
     * @return the dto object, that created from certificate and tagSet
     */
    public GiftCertificateDto convertToDto(GiftCertificate certificate) {

        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setDescription(certificate.getDescription());
        dto.setName(certificate.getName());
        dto.setCreateDate(certificate.getCreateDate());
        dto.setLastUpdateDate(certificate.getLastUpdateDate());
        dto.setDuration(certificate.getDuration());
        dto.setPrice(certificate.getPrice());
        dto.setId(certificate.getId());
        dto.setTags(certificate.getTags());
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
        certificate.setTags(dto.getTags());
        return certificate;
    }


    public Set<GiftCertificateDto> convertToDtos(Set<GiftCertificate> orderSet) {

        return orderSet.stream().map(this::convertToDto).collect(Collectors.toSet());
    }

    public Set<GiftCertificate> convertToEntities(Set<GiftCertificateDto> orderSet) {

        return orderSet.stream().map(this::convertToEntity).collect(Collectors.toSet());
    }
}
