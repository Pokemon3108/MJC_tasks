package com.epam.esm.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.esm.comparator.GiftCertificateSortService;
import com.epam.esm.comparator.GiftCertificateSortServiceImpl;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

@TestInstance(Lifecycle.PER_CLASS)
class GiftCertificateImplTest {

    private GiftCertificateServiceImpl service = new GiftCertificateServiceImpl();

    @Mock
    GiftCertificateDao certificateDao = new GiftCertificateDaoImpl();

    @Mock
    TagDao tagDao = new TagDaoImpl();

    @Mock
    GiftCertificateSortService giftCertificateSortService = new GiftCertificateSortServiceImpl();

    @Mock
    GiftCertificateDtoConverter dtoConverter = new GiftCertificateDtoConverter();

    GiftCertificate certificate;

    GiftCertificateDto certificateDto;

    @BeforeAll
    void init() {

        MockitoAnnotations.openMocks(this);
        service.setTagDao(tagDao);
        service.setCertificateDao(certificateDao);
        service.setDtoConverter(dtoConverter);
        service.setGiftCertificateSortService(giftCertificateSortService);

        BigDecimal price = BigDecimal.ONE;
        int duration = 10;
        String description = "description";
        String name = "name";
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("tag1", 1L), new Tag("tag2", 2L)));

        certificate = new GiftCertificate();
        certificate.setPrice(price);
        certificate.setDuration(duration);
        certificate.setDescription(description);
        certificate.setName(name);

        certificateDto=new GiftCertificateDto();
        certificateDto.setName(name);
        certificateDto.setDescription(description);
        certificateDto.setPrice(price);
        certificateDto.setDuration(duration);
        tags.forEach(t -> certificateDto.addTag(t));
    }

    @Test
    void readTest() {

        final long id = 9L;
        certificate.setId(id);
        Set<Long> tagsIds=certificateDto.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<Tag> tags=certificateDto.getTags();

        Mockito.when(certificateDao.read(id)).thenReturn(Optional.of(certificate));
        Mockito.when(tagDao.readCertificateTagsIdsByCertificateId(certificate.getId())).thenReturn(tagsIds);
        tags.forEach(t-> Mockito.when(tagDao.read(t.getId())).thenReturn(Optional.of(t)));
        Mockito.when(dtoConverter.convertToDto(certificate, tags)).thenReturn(certificateDto);

        Assertions.assertEquals(certificateDto, service.read(id));
    }

}
