package com.epam.esm;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.Tag;

@Service
public class SearchParamsService {

    public GiftCertificateDto buildDto(String name, String description, String tagNames) {

        GiftCertificateDto certificate = new GiftCertificateDto();
        certificate.setName(name);
        certificate.setDescription(description);
        if (tagNames != null) {
            certificate.setTags(parseTagNames(tagNames.split(",")));
        }
        return certificate;
    }

    private Set<Tag> parseTagNames(String[] tagNames) {

        return Arrays.stream(tagNames).map(Tag::new).collect(Collectors.toSet());
    }

}
