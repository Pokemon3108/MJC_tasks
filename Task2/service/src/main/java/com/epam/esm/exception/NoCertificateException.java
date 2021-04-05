package com.epam.esm.exception;

public class NoCertificateException extends RuntimeException {
    public NoCertificateException() {
        super();
    }

    public NoCertificateException(String message) {
        super(message);
    }

    public NoCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCertificateException(Throwable cause) {
        super(cause);
    }

    protected NoCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
