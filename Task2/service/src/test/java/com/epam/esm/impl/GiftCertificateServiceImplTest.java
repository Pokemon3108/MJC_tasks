package com.epam.esm.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import com.epam.esm.TagService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.SizeLimitException;
import com.epam.esm.exception.NoIdException;
import com.epam.esm.exception.NoPageException;
import com.epam.esm.exception.certificate.DuplicateCertificateException;
import com.epam.esm.exception.certificate.NoCertificateException;

class GiftCertificateServiceImplTest {

    GiftCertificateDto certificateDto;

    GiftCertificateDao certificateDao;
    TagService tagService;
    OrderDao orderDao;

    GiftCertificateServiceImpl certificateService;

    static Object[][] findByParamsTestData() {

        final int validPage = 7;
        final int validSize = 20;

        final int negativeSize = -10;
        final int bigSize = 1000;
        final int negativePage = -17;

        final Class<? extends Exception> maxSizeLimitException = SizeLimitException.class;
        final Class<? extends Exception> noPageException = NoPageException.class;

        return new Object[][]{
                {validPage, negativeSize, maxSizeLimitException},
                {validPage, bigSize, maxSizeLimitException},
                {validPage, validSize, noPageException},
                {negativePage, validSize, noPageException},
        };
    }

    @BeforeEach
    void init() {

        certificateDao = mock(GiftCertificateDaoImpl.class);
        tagService = mock(TagServiceImpl.class);
        orderDao = mock(OrderDaoImpl.class);
        certificateService = new GiftCertificateServiceImpl(certificateDao, tagService, orderDao);

        BigDecimal price = BigDecimal.ONE;
        int duration = 10;
        String description = "description";
        String name = "name";
        Set<TagDto> tags = new HashSet<>(Arrays.asList(new TagDto(1L, "tag1"), new TagDto(2L, "tag2")));

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
        final String certificateName = "cert";
        certificateDto.setId(id);
        certificateDto.setName(certificateName);
        Set<TagDto> tags = new HashSet<>(Arrays.asList(new TagDto("tag1"), new TagDto("tag2")));
        Set<TagDto> tagsWithIds = new HashSet<>(Arrays.asList(new TagDto(1L, "tag1"), new TagDto("tag2")));

        Mockito.when(certificateDao.readCertificateByName(certificateDto.getName()))
                .thenReturn(Optional.empty());
        Mockito.when(tagService.bindTagsWithIds(tags)).thenReturn(tagsWithIds);
        Mockito.when(certificateDao.insert(certificateDto)).thenReturn(id);
        Mockito.when(certificateDao.read(id)).thenReturn(Optional.ofNullable(certificateDto));

        Assertions.assertEquals(certificateDto, certificateService.add(certificateDto));

        Mockito.verify(certificateDao, times(1)).readCertificateByName(certificateDto.getName());
        Mockito.verify(tagService, times(1)).bindTagsWithIds(tagsWithIds);
    }

    @Test
    void throwsDuplicateCertificateExceptionReadTest() {

        final String name = certificateDto.getName();
        Mockito.when(certificateDao.readCertificateByName(name)).thenReturn(Optional.of(certificateDto));
        Assertions.assertThrows(DuplicateCertificateException.class, () -> certificateService.add(certificateDto));
    }

    @Test
    void readTest() {

        final long id = 9L;
        certificateDto.setId(id);
        Mockito.when(certificateDao.read(id)).thenReturn(Optional.of(certificateDto));

        Assertions.assertEquals(certificateDto, certificateService.read(id));
    }

    @Test
    void throwsNoCertificateExceptionReadTest() {

        final long id = 9L;
        Mockito.when(certificateDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoCertificateException.class, () -> certificateService.read(id));
    }

    @Test
    void throwsNoIdExceptionUpdateTest() {

        Assertions.assertThrows(NoIdException.class, () -> certificateService.update(certificateDto));
    }

    @Test
    void throwsNoCertificateExceptionUpdateTest() {

        final long id = 9L;
        certificateDto.setId(id);
        Mockito.when(certificateDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoCertificateException.class, () -> certificateService.update(certificateDto));
    }

    @Test
    void deleteTest() {

        final long id = 9L;
        certificateDto.setId(id);
        final boolean orderHasCertificate = false;

        Mockito.when(certificateDao.read(id)).thenReturn(Optional.of(certificateDto));
        Mockito.when(orderDao.anyOrderHasCertificate(certificateDto)).thenReturn(orderHasCertificate);

        certificateService.delete(id);

        Mockito.verify(certificateDao, times(1)).delete(certificateDto);
    }

    @Test
    void throwsNoCertificateExceptionDeleteTest() {

        final long id = 9L;
        Mockito.when(certificateDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoCertificateException.class, () -> certificateService.delete(id));
    }

    @Test
    void findByParamsTest() {

        final int page = 3;
        final int size = 5;

        GiftCertificateDto dtoForSearch = new GiftCertificateDto();
        dtoForSearch.setName("certificate");

        GiftCertificateDto dto1 = new GiftCertificateDto();
        dto1.setName("certificate1");
        GiftCertificateDto dto2 = new GiftCertificateDto();
        dto1.setName("certificate2");
        GiftCertificateDto dto3 = new GiftCertificateDto();
        dto1.setName("certificate3");
        List<GiftCertificateDto> certificateDtos = Arrays.asList(dto1, dto2, dto3);

        Mockito.when(certificateDao.countFoundCertificates(dtoForSearch)).thenReturn(100L);
        Mockito.when(certificateDao.findCertificateByParams(page, size, dtoForSearch, null))
                .thenReturn(certificateDtos);

        Assertions.assertArrayEquals(certificateDtos.toArray(),
                certificateService.findByParams(page, size, dtoForSearch, null).toArray());
    }

    @ParameterizedTest
    @MethodSource("findByParamsTestData")
    void findByParamsThrowsExceptionsTest(final int page, final int size,
            final Class<? extends Exception> exceptionClass) {

        final GiftCertificateDto certificateDto = new GiftCertificateDto();
        final long certificatesAmount = 110;
        Mockito.when(certificateDao.countFoundCertificates(certificateDto)).thenReturn(certificatesAmount);
        Assertions.assertThrows(exceptionClass,
                () -> certificateService.findByParams(page, size, certificateDto, null));
    }

    @Test
    void countFoundByParamsCertificatesTest() {

        final long amount = 200;
        Mockito.when(certificateDao.countFoundCertificates(certificateDto)).thenReturn(amount);
        certificateService.countFoundByParamsCertificates(certificateDto);
        Assertions.assertEquals(amount, certificateService.countFoundByParamsCertificates(certificateDto));
    }

}
