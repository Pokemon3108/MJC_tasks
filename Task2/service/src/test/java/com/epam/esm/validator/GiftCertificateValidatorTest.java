package com.epam.esm.validator;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

import com.epam.esm.dto.GiftCertificateDto;

class GiftCertificateValidatorTest {

    GiftCertificateValidator validator = new GiftCertificateValidator();


    static Object[][] validateTestData() {

        GiftCertificateDto noNameDto = new GiftCertificateDto();
        noNameDto.setDescription("description");
        noNameDto.setDuration(10);
        noNameDto.setPrice(BigDecimal.ONE);
        final String NO_NAME_MESSAGE = "empty_name";

        GiftCertificateDto negativeDurationDto = new GiftCertificateDto();
        negativeDurationDto.setDescription("description");
        negativeDurationDto.setName("name");
        negativeDurationDto.setDuration(-10);
        negativeDurationDto.setPrice(BigDecimal.ONE);
        final String NEGATIVE_DURATION_MESSAGE = "negative_duration";

        GiftCertificateDto noDescriptionDto = new GiftCertificateDto();
        noDescriptionDto.setName("name");
        noDescriptionDto.setDuration(10);
        noDescriptionDto.setPrice(BigDecimal.ONE);
        final String NO_DESCRIPTION = "empty_description";

        return new Object[][]{
                {noNameDto, NO_NAME_MESSAGE},
                {negativeDurationDto, NEGATIVE_DURATION_MESSAGE},
                {noDescriptionDto, NO_DESCRIPTION}
        };
    }

    @ParameterizedTest
    @MethodSource("validateTestData")
    void validateEmptyCertificateFields(GiftCertificateDto certificateDto, final String message) {

        Errors errors = Mockito.mock(Errors.class);
        validator.validate(certificateDto, errors);
        Mockito.verify(errors, Mockito.times(1)).reject(message);
    }

    @Test
    void validateFilledCertificate() {

        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setDescription("description");
        dto.setName("name");
        dto.setDuration(10);
        dto.setPrice(BigDecimal.ONE);

        Errors errors = Mockito.mock(Errors.class);
        validator.validate(dto, errors);
        Mockito.verify(errors, Mockito.times(0)).reject(Mockito.anyString());
    }

}
