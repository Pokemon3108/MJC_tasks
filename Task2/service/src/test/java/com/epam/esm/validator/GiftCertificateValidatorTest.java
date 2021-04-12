package com.epam.esm.validator;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

import com.epam.esm.dto.GiftCertificateDto;

class GiftCertificateValidatorTest {

    private GiftCertificateValidator validator = new GiftCertificateValidator();

    @Test
    void validateEmptyCertificate() {

        Errors errors = Mockito.mock(Errors.class);
        validator.validate(new GiftCertificateDto(), errors);
        Mockito.verify(errors, Mockito.times(1)).reject(Mockito.anyString());
    }

    @Test
    void validateFilledCertificate() {

        GiftCertificateDto dto=new GiftCertificateDto();
        dto.setDescription("description");
        dto.setName("name");
        dto.setDuration(10);
        dto.setPrice(BigDecimal.ONE);

        Errors errors = Mockito.mock(Errors.class);
        validator.validate(dto, errors);
        Mockito.verify(errors, Mockito.times(0)).reject(Mockito.anyString());
    }

}
