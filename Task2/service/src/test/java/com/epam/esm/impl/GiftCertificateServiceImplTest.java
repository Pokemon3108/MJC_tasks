package com.epam.esm.impl;

import static org.mockito.ArgumentMatchers.eq;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.esm.TagService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateCertificateException;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoIdException;

class GiftCertificateServiceImplTest {

    GiftCertificateServiceImpl service = new GiftCertificateServiceImpl();

    @Mock
    GiftCertificateDao certificateDao = new GiftCertificateDaoImpl();

    @Mock
    TagDao tagDao = new TagDaoImpl();

    @Mock
    TagService tagService = new TagServiceImpl();

    GiftCertificateDtoConverter dtoConverter = new GiftCertificateDtoConverter();

    GiftCertificate certificate;

    GiftCertificateDto certificateDto;

    @BeforeEach
    void init() {

        MockitoAnnotations.openMocks(this);
        service.setTagDao(tagDao);
        service.setCertificateDao(certificateDao);
        service.setDtoConverter(dtoConverter);
        service.setTagService(tagService);

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

        certificateDto = new GiftCertificateDto();
        certificateDto.setName(name);
        certificateDto.setDescription(description);
        certificateDto.setPrice(price);
        certificateDto.setDuration(duration);
        tags.forEach(t -> certificateDto.addTag(t));
    }

    @Test
    void addTest() {

        final long id = 9L;
        Set<String> tagsNames = certificateDto.getTags().stream().map(Tag::getName).collect(Collectors.toSet());

        Mockito.when(certificateDao.insert(certificateDto)).thenReturn(id);
        Mockito.when(tagDao.readTagsByNames(tagsNames)).thenReturn(certificateDto.getTags());
        certificateDto.getTags().forEach(t -> Mockito.when(tagDao.insert(t)).thenReturn(t.getId()));

        Long generatedId = service.add(certificateDto);
        Mockito.verify(tagService, Mockito.times(1)).setTagsId(certificateDto.getTags());
        Mockito.verify(tagDao, Mockito.times(1)).bindCertificateTags(Mockito.any(), eq(id));

        Assertions.assertEquals(id, generatedId);
    }

    @Test
    void throwsDuplicateCertificateExceptionReadTest() {

        final String name = certificate.getName();
        Mockito.when(certificateDao.readCertificateByName(name)).thenReturn(Optional.of(certificate));
        Assertions.assertThrows(DuplicateCertificateException.class, () -> service.add(certificateDto));
    }

    @Test
    void readTest() {

        final long id = 9L;
        certificate.setId(id);
        certificateDto.setId(id);
        Set<Long> tagsIds = certificateDto.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<Tag> tags = certificateDto.getTags();

        Mockito.when(certificateDao.read(id)).thenReturn(Optional.of(certificate));
        Mockito.when(tagDao.readCertificateTagsIdsByCertificateId(certificate.getId())).thenReturn(tagsIds);
        tags.forEach(t -> Mockito.when(tagDao.read(t.getId())).thenReturn(Optional.of(t)));

        Assertions.assertEquals(certificateDto, service.read(id));
    }

    @Test
    void throwsNoCertificateExceptionReadTest() {

        final long id = 9L;
        Mockito.when(certificateDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoCertificateException.class, () -> service.read(id));
    }

    @Test
    void throwsNoIdExceptionUpdateTest() {

        Assertions.assertThrows(NoIdException.class, () -> service.update(certificateDto));

    }

    @Test
    void throwsNoCertificateExceptionUpdateTest() {

        final long id = 9L;
        certificateDto.setId(id);
        Mockito.when(certificateDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoCertificateException.class, () -> service.update(certificateDto));
    }

    @Test
    void throwsNoCertificateExceptionDeleteTest() {

        final long id = 9L;
        Mockito.when(certificateDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoCertificateException.class, () -> service.delete(id));
    }

    @Test
    void findByParamsTest() {

        String certificateNameToFind = "certificate1";
        String descriptionToFind = "description2";
        Tag tag = new Tag("nature");
        Set<Long> certificateTagsIds = new HashSet<>(Arrays.asList(1L, 2L));
        Set<Tag> certificateTags = new HashSet<>(Arrays.asList(new Tag("nature", 1L), new Tag("new", 2L)));
        long certificateId = 1L;

        GiftCertificate certificate1 = new GiftCertificate();
        certificate1.setName(certificateNameToFind);
        certificate1.setDescription("description1");

        GiftCertificate certificate2 = new GiftCertificate();
        certificate2.setName("certificate2");
        certificate2.setDescription(descriptionToFind);

        GiftCertificate certificate12 = new GiftCertificate();
        certificate12.setName(certificateNameToFind);
        certificate12.setDescription(descriptionToFind);
        certificate12.setId(certificateId);

        certificateDto.setName(certificateNameToFind);
        certificateDto.setDescription(descriptionToFind);
        certificateDto.setTags(new HashSet<>(Collections.singletonList(tag)));

        List<GiftCertificate> certificatesWithNameAndDescription = Collections.singletonList(certificate12);
        Mockito.when(certificateDao.findCertificateByParams(certificateDto))
                .thenReturn(certificatesWithNameAndDescription);
        List<GiftCertificate> certificatesWithTags = Arrays.asList(certificate1, certificate2, certificate12);
        Mockito.when(certificateDao.findCertificateByTagName(certificateDto.getTag(0).getName()))
                .thenReturn(certificatesWithTags);
        Mockito.when(tagDao.readCertificateTagsIdsByCertificateId(Mockito.anyLong())).thenReturn(certificateTagsIds);
        Mockito.when(tagDao.readTagsByIds(certificateTagsIds)).thenReturn(certificateTags);

        GiftCertificateDto dto12 = new GiftCertificateDto();
        dto12.setTags(certificateTags);
        dto12.setName(certificate12.getName());
        dto12.setDescription(certificate12.getDescription());
        dto12.setId(certificateId);
        List<GiftCertificateDto> dtos = Collections.singletonList(dto12);

        Assertions.assertArrayEquals(dtos.toArray(), service.findByParams(certificateDto).toArray());
    }

}
