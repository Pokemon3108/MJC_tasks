package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Service;

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
}
