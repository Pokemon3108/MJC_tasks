package com.epam.esm.exception;


/**
 * NotFullCertificateException is thrown when certificate has empty required fields
 */
public class NotFullCertificateException extends RuntimeException {

    /**
     * Instantiates a new Not full certificate exception.
     */
    public NotFullCertificateException() {

    }

    /**
     * {@inheritDoc}
     */
    public NotFullCertificateException(String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public NotFullCertificateException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public NotFullCertificateException(Throwable cause) {

        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public NotFullCertificateException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
