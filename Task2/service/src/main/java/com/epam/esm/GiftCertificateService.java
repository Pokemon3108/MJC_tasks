package com.epam.esm;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SortParamsDto;

/**
 * The interface for gift certificate CRUD operations
 */
public interface GiftCertificateService {

    /**
     * Add certificate into storage
     *
     * @param certificate that will be saved in storage
     * @return the id of certificate
     */
    GiftCertificateDto add(GiftCertificateDto certificate);

    /**
     * Read gift certificate from storage by id
     *
     * @param id of certificate
     * @return the gift certificate
     */
    GiftCertificateDto read(long id);

    /**
     * Update certificate in storage
     *
     * @param certificate it is already updated
     */
    void update(GiftCertificateDto certificate);

    /**
     * Delete gift certificate from storage by id
     *
     * @param id of certificate
     */
    void delete(long id);

    /**
     * @param page        - the page number
     * @param size        - maximum size of result list
     * @param certificate with params for search
     * @param sortParams  the sorting params
     * @return list of found and sorted certificates
     */
    List<GiftCertificateDto> findByParams(int page, int size, GiftCertificateDto certificate, SortParamsDto sortParams);

    /**
     * @param dto - the DTO object with search params
     * @return amount of found certificates
     */
    long countFoundByParamsCertificates(GiftCertificateDto dto);
}
