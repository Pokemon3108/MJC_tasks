package com.epam.esm.exception.user;

/**
 * Throws when there is no users in storage
 */
public class NoUsersException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public NoUsersException() {

        super();
    }

    /**
     * {@inheritDoc}
     */
    public NoUsersException(String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public NoUsersException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public NoUsersException(Throwable cause) {

        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    protected NoUsersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
