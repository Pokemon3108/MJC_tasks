package com.epam.esm.validator;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.certificate.IllegalCertificateProperties;

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
    void validateCreateCertificateWithEmptyFields(GiftCertificateDto certificateDto, final String message) {

        Assertions.assertThrows(IllegalCertificateProperties.class, () -> validator.validateCreate(certificateDto),
                message);
    }

    @Test
    void validateUpdateCertificate() {

        final String NEGATIVE_PRICE_MESSAGE = "negative_price";
        final GiftCertificateDto dto = new GiftCertificateDto();
        dto.setDescription("description");
        dto.setName("name");
        dto.setDuration(-10);
        dto.setPrice(BigDecimal.ONE);

        Assertions.assertThrows(IllegalCertificateProperties.class, () -> validator.validateCreate(dto),
                NEGATIVE_PRICE_MESSAGE);
    }

}
