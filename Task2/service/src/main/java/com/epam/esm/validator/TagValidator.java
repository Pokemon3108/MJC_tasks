package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service("tagValidator")
public class TagValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Tag.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Tag tag=(Tag) target;
        if (tag.getName()==null) {
            errors.reject("empty_name");
        }
    }
}
