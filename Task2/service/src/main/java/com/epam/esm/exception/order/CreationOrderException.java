package com.epam.esm.exception.order;

public class CreationOrderException extends RuntimeException {

    public CreationOrderException() {

    }

    public CreationOrderException(String message) {

        super(message);
    }

    public CreationOrderException(String message, Throwable cause) {

        super(message, cause);
    }

    public CreationOrderException(Throwable cause) {

        super(cause);
    }

    public CreationOrderException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
