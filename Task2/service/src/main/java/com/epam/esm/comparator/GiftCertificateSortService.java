package com.epam.esm.comparator;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;


/**
 * The interface Gift certificate sort service uses for sorting certificates
 */
public interface GiftCertificateSortService {

    /**
     * Sort list.
     *
     * @param certificates the certificates to be sorted
     * @param params       the params of sorting
     * @param direction    the direction of sorting
     * @return the sorted list
     */
    List<GiftCertificateDto> sort(List<GiftCertificateDto> certificates, List<String> params, Direction direction);
}
