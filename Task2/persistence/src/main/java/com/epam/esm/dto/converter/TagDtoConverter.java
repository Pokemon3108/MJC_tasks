package com.epam.esm.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

@Component
public class TagDtoConverter extends Converter<Tag, TagDto> {

    private GiftCertificateDtoConverter certificateDtoConverter;

    @Autowired
    public TagDtoConverter(GiftCertificateDtoConverter certificateDtoConverter) {

        this.certificateDtoConverter = certificateDtoConverter;
    }

    @Override
    public Tag convertToEntity(TagDto dto) {

        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setId(dto.getId());
        tag.setCertificates(certificateDtoConverter.convertToEntities(dto.getCertificates()));
        return tag;
    }

    @Override
    public TagDto convertToDto(Tag tag) {

        TagDto tagDto = new TagDto();
        tagDto.setName(tag.getName());
        tagDto.setId(tag.getId());
        tagDto.setCertificates(certificateDtoConverter.convertToDtos(tag.getCertificates()));
        return tagDto;
    }


}
