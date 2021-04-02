package com.epam.esm.repos.exception;


/**
 * IllegalOperationException is thrown, when current operation is forbidden
 */
public class IllegalOperationException extends RuntimeException {
    /**
     * Instantiates a new Illegal operation exception.
     */
    public IllegalOperationException() {
        super();
    }

    /**
     * Instantiates a new Illegal operation exception.
     *
     * @param message additional info about exception
     */
    public IllegalOperationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Illegal operation exception.
     *
     * @param message additional info about exception
     * @param cause   of exception
     */
    public IllegalOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Illegal operation exception.
     *
     * @param cause of exception
     */
    public IllegalOperationException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Illegal operation exception.
     *
     * @param message            additional info about exception
     * @param cause              of exception
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    protected IllegalOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
