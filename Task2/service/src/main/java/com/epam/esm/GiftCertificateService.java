package com.epam.esm;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.esm.dto.GiftCertificateDto;

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
    Long add(GiftCertificateDto certificate);

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
     * Search certificates by params
     *
     * @param certificate with params for search
     * @return list of found certificates
     */
    List<GiftCertificateDto> findByParams(GiftCertificateDto certificate);
}
