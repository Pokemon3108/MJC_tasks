package com.epam.esm.validator;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

import com.epam.esm.entity.Tag;

class TagValidatorTest {

    private TagValidator tagValidator = new TagValidator();

    @Test
    void validateEmptyTagTest() {

        Errors errors = Mockito.mock(Errors.class);
        tagValidator.validate(new Tag(), errors);
        Mockito.verify(errors, Mockito.times(1)).reject(Mockito.anyString());
    }

    @Test
    void validateFilledTagTest() {

        Errors errors = Mockito.mock(Errors.class);
        tagValidator.validate(new Tag("sport", 1L), errors);
        Mockito.verify(errors, Mockito.times(0)).reject(Mockito.anyString());
    }

}
