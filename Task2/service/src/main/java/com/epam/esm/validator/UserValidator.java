package com.epam.esm.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.epam.esm.exception.user.InvalidUserPropertiesException;

@Service
public class UserValidator {

    private static final String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{8,}";

    public void validatePassword(String password) {

        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new InvalidUserPropertiesException("invalid_password");
        }
    }

}
