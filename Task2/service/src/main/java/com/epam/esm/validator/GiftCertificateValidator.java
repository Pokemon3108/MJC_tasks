package com.epam.esm.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.epam.esm.entity.GiftCertificate;

@Service("certificateValidator")
public class GiftCertificateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {

        return GiftCertificate.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        GiftCertificate certificate = (GiftCertificate) target;
        if (certificate.getName() == null) {
            errors.reject("empty_name");
        } else if (certificate.getDescription() == null) {
            errors.reject("empty_description");
        } else if (certificate.getDuration() == null) {
            errors.reject("empty_duration");
        } else if (certificate.getDuration() <= 0) {
            errors.reject("negative_duration");
        } else if (certificate.getPrice() == null) {
            errors.reject("price", "empty_price");
        } else if (certificate.getPrice().doubleValue() <= 0.0) {
            errors.reject("negative_price");
        } else if (certificate.getTags().isEmpty()) {
            errors.reject("no_tags");
        }
    }
}
