package com.epam.esm.exception.user;

public class NoUserWithIdException extends RuntimeException {

    private long id;

    public NoUserWithIdException(long id) {

        this.id = id;
    }

    public NoUserWithIdException() {

    }

    public NoUserWithIdException(String message) {

        super(message);
    }

    public NoUserWithIdException(String message, Throwable cause) {

        super(message, cause);
    }

    public NoUserWithIdException(Throwable cause) {

        super(cause);
    }

    public NoUserWithIdException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public long getId() {

        return id;
    }
}
