package com.epam.esm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.OrderService;
import com.epam.esm.PageService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.Page;

/**
 * Implementation of page service
 */
@Service
public class PageServiceImpl implements PageService {

    private GiftCertificateService certificateService;

    private OrderService orderService;

    @Autowired
    public PageServiceImpl(GiftCertificateService certificateService, OrderService orderService) {

        this.certificateService = certificateService;
        this.orderService = orderService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page buildPageForCertificateSearch(int pageNumber, int size,
            GiftCertificateDto giftCertificateDto, List<GiftCertificateDto> certificates) {

        long totalSize = certificateService.countFoundByParamsCertificates(giftCertificateDto);
        int totalPages = 0;
        if (size != 0) {
            totalPages = totalSize % size == 0 ? (int) totalSize / size : (int) totalSize / size + 1;
        }
        return new Page(certificates.size(), totalSize, totalPages, pageNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page buildPageForUserOrderSearch(int pageNumber, int size, UserDto user, List<OrderDto> orders) {

        long totalSize = orderService.countUserOrders(user);
        int totalPages = 0;
        if (size != 0) {
            totalPages = totalSize % size == 0 ? (int) totalSize / size : (int) totalSize / size + 1;
        }
        return new Page(orders.size(), totalSize, totalPages, pageNumber);
    }
}
