package com.epam.esm.validator;

import org.springframework.stereotype.Service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.certificate.IllegalCertificatePropertiesException;

/**
 * This class uses for validation {@code GiftCertificate} objects
 */
@Service("certificateValidator")
public class GiftCertificateValidator {

    public void validateCreation(GiftCertificateDto certificate) {

        if (certificate.getName() == null) {
            throw new IllegalCertificatePropertiesException("empty_name");
        } else if (certificate.getDescription() == null) {
            throw new IllegalCertificatePropertiesException("empty_description");
        } else if (certificate.getDuration() == null) {
            throw new IllegalCertificatePropertiesException("empty_duration");
        } else if (certificate.getDuration() <= 0) {
            throw new IllegalCertificatePropertiesException("negative_duration");
        } else if (certificate.getPrice() == null) {
            throw new IllegalCertificatePropertiesException("empty_price");
        } else if (certificate.getPrice().doubleValue() <= 0.0) {
            throw new IllegalCertificatePropertiesException("negative_price");
        }
    }

    public void validateUpdate(GiftCertificateDto certificate) {

        if (certificate.getPrice() != null && certificate.getPrice().doubleValue() < 0) {
            throw new IllegalCertificatePropertiesException("negative_price");
        } else if (certificate.getDuration() != null && certificate.getDuration() < 0) {
            throw new IllegalCertificatePropertiesException("negative_duration");
        }
    }
}
