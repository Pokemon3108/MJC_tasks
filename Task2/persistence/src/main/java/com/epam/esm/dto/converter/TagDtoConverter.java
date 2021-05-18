package com.epam.esm.dto.converter;

import org.springframework.stereotype.Component;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

@Component
public class TagDtoConverter extends Converter<Tag, TagDto> {

    @Override
    public Tag convertToEntity(TagDto dto) {

        if (dto == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setId(dto.getId());
        return tag;
    }

    @Override
    public TagDto convertToDto(Tag tag) {

        if (tag == null) {
            return null;
        }
        TagDto tagDto = new TagDto();
        tagDto.setName(tag.getName());
        tagDto.setId(tag.getId());
        return tagDto;
    }


}
