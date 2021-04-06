package com.epam.esm.exception;

public class NoIdException extends RuntimeException {
    public NoIdException() {
        super();
    }

    public NoIdException(String message) {
        super(message);
    }

    public NoIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoIdException(Throwable cause) {
        super(cause);
    }

    protected NoIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
