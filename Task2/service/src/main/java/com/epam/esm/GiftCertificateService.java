package com.epam.esm;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.esm.entity.GiftCertificate;

/**
 * The interface for gift certificate CRUD operations
 */
@Service
public interface GiftCertificateService {

    /**
     * Add certificate into storage
     *
     * @param certificate that will be saved in storage
     * @return the id of certificate
     */
    Long add(GiftCertificate certificate);

    /**
     * Read gift certificate from storage by id
     *
     * @param id of certificate
     * @return the gift certificate
     */
    GiftCertificate read(long id);

    /**
     * Update certificate in storage
     *
     * @param certificate it is already updated
     */
    void update(GiftCertificate certificate);

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
    List<GiftCertificate> findByParams(GiftCertificate certificate);

    /**
     * Sort certificates by params
     *
     * @param params of sorting
     * @return sorted list of certificates
     */
    List<GiftCertificate> sortByParams(List<GiftCertificate> certificates,
            List<String> params, String direction);

}
