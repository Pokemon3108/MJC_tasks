package com.epam.esm.exception.user;

/**
 * Throws when created user has invalid values of properties
 */
public class InvalidUserPropertiesException extends RuntimeException {

    public InvalidUserPropertiesException() {

    }

    public InvalidUserPropertiesException(String message) {

        super(message);
    }

    public InvalidUserPropertiesException(String message, Throwable cause) {

        super(message, cause);
    }

    public InvalidUserPropertiesException(Throwable cause) {

        super(cause);
    }

    public InvalidUserPropertiesException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
