package com.epam.esm.exception;

public class SizeLimitException extends RuntimeException {

    private int maxSize;

    private int minSize;

    public SizeLimitException(int maxSize, int minSize) {

        this.maxSize = maxSize;
        this.minSize=minSize;
    }

    public SizeLimitException() {

        super();
    }

    public SizeLimitException(String message) {

        super(message);
    }

    public SizeLimitException(String message, Throwable cause) {

        super(message, cause);
    }

    public SizeLimitException(Throwable cause) {

        super(cause);
    }

    protected SizeLimitException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getMaxSize() {

        return maxSize;
    }

    public int getMinSize() {

        return minSize;
    }
}
