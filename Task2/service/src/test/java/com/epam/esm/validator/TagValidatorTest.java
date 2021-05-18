package com.epam.esm.validator;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

import com.epam.esm.dto.TagDto;

class TagValidatorTest {

    private TagValidator tagValidator = new TagValidator();

    @Test
    void validateEmptyTagTest() {

        String errorName = "empty_name";
        Errors errors = Mockito.mock(Errors.class);
        tagValidator.validate(new TagDto(), errors);
        Mockito.verify(errors, Mockito.times(1)).reject(errorName);
    }

    @Test
    void validateFilledTagTest() {

        String errorName = "empty_name";
        Errors errors = Mockito.mock(Errors.class);
        tagValidator.validate(new TagDto(1L, "sport"), errors);
        Mockito.verify(errors, Mockito.times(0)).reject(errorName);
    }

}
