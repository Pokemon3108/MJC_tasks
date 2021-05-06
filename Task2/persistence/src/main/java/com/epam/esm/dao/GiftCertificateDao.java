package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.dto.GiftCertificateDto;


/**
 * Interface for work with storage for certificate
 */
public interface GiftCertificateDao {

    /**
     * @param certificateDto that will be saved in storage
     * @return id of {@code certificateDto} from storage
     */
    Long insert(GiftCertificateDto certificateDto);

    /**
     * @param certificateDto certificate with params for update
     */
    void update(GiftCertificateDto certificateDto);

    /**
     * Delete certificate
     *
     * @param certificate to be deleted
     */
    void delete(GiftCertificateDto certificate);

    /**
     * Read certificate by id
     *
     * @param id
     * @return certificate from storage
     */
    Optional<GiftCertificateDto> read(long id);

    /**
     * Read certificate by name
     *
     * @param certificateName - the name of certificate
     * @return certificate from storage
     */
    Optional<GiftCertificateDto> readCertificateByName(String certificateName);

    /**
     * @param certificateDto with filled params, for which the search will be performed
     * @return list of found certificates
     */
    List<GiftCertificateDto> findCertificateByParams(int page, int size, GiftCertificateDto certificateDto);

    /**
     * @return amount of certificates in storage
     */
    long getAllCount();

    /**
     * @param dto with filled params, for which the search will be performed
     * @return total amount of certificates, searched by params in storage
     */
    long countFoundCertificates(GiftCertificateDto dto);
}
