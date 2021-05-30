package com.epam.esm.impl;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.Page;

class PageServiceImplTest {

    GiftCertificateServiceImpl certificateService;

    OrderServiceImpl orderService;

    PageServiceImpl pageService;

    @BeforeEach
    void init() {

        certificateService = mock(GiftCertificateServiceImpl.class);
        orderService = mock(OrderServiceImpl.class);
        pageService = new PageServiceImpl(certificateService, orderService);
    }

    @Test
    void buildPageForCertificateSearchTest() {

        final long totalElements = 7000;
        List<GiftCertificateDto> dtos = new ArrayList<>(
                Arrays.asList(new GiftCertificateDto(), new GiftCertificateDto(), new GiftCertificateDto()));
        final int maxSize = 7;
        final int totalPages = 1000;
        final int pageNumber = 9;

        GiftCertificateDto certificateDto = new GiftCertificateDto();
        Mockito.when(certificateService.countFoundByParamsCertificates(certificateDto)).thenReturn(totalElements);

        Assertions.assertEquals(new Page(dtos.size(), totalElements, totalPages, pageNumber),
                pageService.buildPageForCertificateSearch(pageNumber, maxSize, certificateDto, dtos));

    }

    @Test
    void buildPageForUserOrderSearchTest() {

        final long totalElements = 7001;
        List<OrderDto> dtos = new ArrayList<>(Arrays.asList(new OrderDto(), new OrderDto()));
        final int maxSize = 7;
        final int totalPages = 1001;
        final int pageNumber = 9;

        UserDto userDto = new UserDto();
        Mockito.when(orderService.countUserOrders(userDto)).thenReturn(totalElements);

        Assertions.assertEquals(new Page(dtos.size(), totalElements, totalPages, pageNumber),
                pageService.buildPageForUserOrderSearch(pageNumber, maxSize, userDto, dtos));
    }
}
