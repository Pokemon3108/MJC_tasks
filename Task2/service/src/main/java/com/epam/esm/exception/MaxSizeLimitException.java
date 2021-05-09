package com.epam.esm.exception;

public class MaxSizeLimitException extends RuntimeException {

    private int maxSize;

    public MaxSizeLimitException(int maxSize) {

        this.maxSize = maxSize;
    }

    public MaxSizeLimitException() {

        super();
    }

    public MaxSizeLimitException(String message) {

        super(message);
    }

    public MaxSizeLimitException(String message, Throwable cause) {

        super(message, cause);
    }

    public MaxSizeLimitException(Throwable cause) {

        super(cause);
    }

    protected MaxSizeLimitException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getMaxSize() {

        return maxSize;
    }
}
