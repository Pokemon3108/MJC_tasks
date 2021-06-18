package com.epam.esm.exception.user;

/**
 * DuplicateTagException when inserted user with already present name
 */
public class DuplicateUserException extends RuntimeException {

    private String username;

    public DuplicateUserException(String username) {

        this.username = username;
    }

    public DuplicateUserException(String message, String username) {

        super(message);
        this.username = username;
    }

    public DuplicateUserException(String message, Throwable cause, String username) {

        super(message, cause);
        this.username = username;
    }

    public DuplicateUserException(Throwable cause, String username) {

        super(cause);
        this.username = username;
    }

    public DuplicateUserException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace,
            String username) {

        super(message, cause, enableSuppression, writableStackTrace);
        this.username = username;
    }

    public String getUsername() {

        return username;
    }
}
