package com.epam.esm;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.Page;

public interface PageService {

    Page buildPageForCertificateSearch(int pageNumber, int size,
            GiftCertificateDto giftCertificateDto, List<GiftCertificateDto> certificates);

    Page buildPageForUserOrderSearch(int pageNumber, int size, UserDto user, List<OrderDto> orders);
}
