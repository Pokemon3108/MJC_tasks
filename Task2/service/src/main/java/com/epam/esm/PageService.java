package com.epam.esm;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.Page;

/**
 * Service for build pagination metadata
 */
public interface PageService {

    /**
     * Build page metadata for search of certificates
     *
     * @param pageNumber         - the number of page
     * @param size               - the amount of content on page
     * @param giftCertificateDto - dto certificate, which properties uses in search
     * @param certificates       - result of search
     * @return page metadata
     */
    Page buildPageForCertificateSearch(int pageNumber, int size,
            GiftCertificateDto giftCertificateDto, List<GiftCertificateDto> certificates);

    /**
     * Build page metadata for search of orders
     *
     * @param pageNumber - the number of page
     * @param size       - the amount of content on page
     * @param user       - the user, whose orders will be looked for
     * @param orders     - result of search
     * @return page metadata
     */
    Page buildPageForUserOrderSearch(int pageNumber, int size, UserDto user, List<OrderDto> orders);
}
