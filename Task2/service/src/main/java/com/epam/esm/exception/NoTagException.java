package com.epam.esm.exception;

/**
 * NoTagException is thrown when required tag doesn't exist
 */
public class NoTagException extends RuntimeException {

    private long tagId;

    /**
     * Instantiates a new No tag exception with tag id
     *
     * @param tagId
     */
    public NoTagException(long tagId) {

        this.tagId = tagId;
    }

    /**
     * {@inheritDoc}
     */
    public NoTagException() {

        super();
    }

    /**
     * {@inheritDoc}
     */
    public NoTagException(String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public NoTagException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public NoTagException(Throwable cause) {

        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    protected NoTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * {@inheritDoc}
     */
    public long getTagId() {

        return tagId;
    }
}
