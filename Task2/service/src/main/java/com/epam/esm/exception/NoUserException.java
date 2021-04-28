package com.epam.esm.exception;

public class NoUserException extends RuntimeException {

    private long id;

    public NoUserException(long id) {

        this.id = id;
    }

    public NoUserException() {

    }

    public NoUserException(String message) {

        super(message);
    }

    public NoUserException(String message, Throwable cause) {

        super(message, cause);
    }

    public NoUserException(Throwable cause) {

        super(cause);
    }

    public NoUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public long getId() {

        return id;
    }
}
