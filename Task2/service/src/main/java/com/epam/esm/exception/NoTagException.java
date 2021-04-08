package com.epam.esm.exception;

public class NoTagException extends RuntimeException {

    private long tagId;

    public NoTagException(long tagId) {

        this.tagId = tagId;
    }

    public NoTagException() {

        super();
    }

    public NoTagException(String message) {

        super(message);
    }

    public NoTagException(String message, Throwable cause) {

        super(message, cause);
    }

    public NoTagException(Throwable cause) {

        super(cause);
    }

    protected NoTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public long getTagId() {

        return tagId;
    }
}
