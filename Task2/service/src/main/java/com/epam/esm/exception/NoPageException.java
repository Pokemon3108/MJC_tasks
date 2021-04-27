package com.epam.esm.exception;

/**
 * Exception throws, when there is no content on {@code page} with {@code size}
 */
public class NoPageException extends RuntimeException {

    private int size;
    private int page;


    /**
     * Instantiates a new No page exception
     *
     * @param page
     * @param size
     */
    public NoPageException(int page, int size) {

        this.page = page;
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    public NoPageException(String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message
     * @param cause   the cause
     */
    public NoPageException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * {@inheritDoc}
     *
     * @param cause the cause
     */
    public NoPageException(Throwable cause) {

        super(cause);
    }

    /**
     * {@inheritDoc}
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public NoPageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getSize() {

        return size;
    }

    public int getPage() {

        return page;
    }
}
