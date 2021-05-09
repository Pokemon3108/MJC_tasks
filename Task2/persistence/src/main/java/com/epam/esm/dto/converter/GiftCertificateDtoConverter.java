package com.epam.esm.dto.converter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;


/**
 * The type Gift certificate dto converter for converting from entity to dto and vice versa
 */
@Component
public class GiftCertificateDtoConverter {

    private TagDtoConverter tagDtoConverter;

    @Autowired
    public GiftCertificateDtoConverter(TagDtoConverter tagDtoConverter) {

        this.tagDtoConverter = tagDtoConverter;
    }

    /**
     * Convert to dto gift certificate dto.
     *
     * @param certificate the certificate entity
     * @return the dto object, that created from certificate and tagSet
     */
    public GiftCertificateDto convertToDto(GiftCertificate certificate) {

        if (certificate == null) {
            return null;
        }
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setDescription(certificate.getDescription());
        dto.setName(certificate.getName());
        dto.setCreateDate(certificate.getCreateDate());
        dto.setLastUpdateDate(certificate.getLastUpdateDate());
        dto.setDuration(certificate.getDuration());
        dto.setPrice(certificate.getPrice());
        dto.setId(certificate.getId());
        dto.setTags(tagDtoConverter.convertToDtos(certificate.getTags()));
        return dto;
    }

    /**
     * Convert dto to entity
     *
     * @param dto
     * @return the gift certificate, that created from {@code dto}
     */
    public GiftCertificate convertToEntity(GiftCertificateDto dto) {

        if (dto == null) {
            return null;
        }
        GiftCertificate certificate = new GiftCertificate();
        certificate.setDescription(dto.getDescription());
        certificate.setName(dto.getName());
        certificate.setCreateDate(dto.getCreateDate());
        certificate.setLastUpdateDate(dto.getLastUpdateDate());
        certificate.setDuration(dto.getDuration());
        certificate.setPrice(dto.getPrice());
        certificate.setId(dto.getId());
        certificate.setTags(tagDtoConverter.convertToEntities(dto.getTags()));
        return certificate;
    }


    public Set<GiftCertificateDto> convertToDtos(Set<GiftCertificate> orderSet) {

        List<GiftCertificateDto> s = orderSet.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new LinkedHashSet<>(s);
    }

    public Set<GiftCertificate> convertToEntities(Set<GiftCertificateDto> orderSet) {

        return orderSet.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toSet());
    }
}
