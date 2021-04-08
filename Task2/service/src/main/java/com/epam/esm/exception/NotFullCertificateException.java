package com.epam.esm.exception;

public class NotFullCertificateException extends RuntimeException {

    public NotFullCertificateException() {

    }

    public NotFullCertificateException(String message) {

        super(message);
    }

    public NotFullCertificateException(String message, Throwable cause) {

        super(message, cause);
    }

    public NotFullCertificateException(Throwable cause) {

        super(cause);
    }

    public NotFullCertificateException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
