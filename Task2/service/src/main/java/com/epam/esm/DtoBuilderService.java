package com.epam.esm;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.esm.dto.SortDirection;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SortParamsDto;
import com.epam.esm.dto.TagDto;

/**
 * Service for build dto objects
 */
@Service
public class DtoBuilderService {

    private static final List<String> sortParams = Arrays.asList("createDate", "name");

    public GiftCertificateDto buildCertificateDto(String name, String description, String tagNames) {

        GiftCertificateDto certificate = new GiftCertificateDto();
        certificate.setName(name);
        certificate.setDescription(description);
        if (tagNames != null) {
            certificate.setTags(parseTagNames(tagNames.split(",")));
        }
        return certificate;
    }

    private Set<TagDto> parseTagNames(String[] tagNames) {

        return Arrays.stream(tagNames).map(TagDto::new).collect(Collectors.toSet());
    }

    public SortParamsDto buildSortParams(String params, String direction) {

        List<String> splitParams = Arrays.asList(params.split(","));
        splitParams = splitParams.stream().filter(sortParams::contains).collect(Collectors.toList());
        SortDirection dir = SortDirection.contains(direction.toUpperCase()) ? SortDirection.valueOf(direction.toUpperCase()) :
                SortDirection.ASC;
        return new SortParamsDto(splitParams, dir);
    }

}
