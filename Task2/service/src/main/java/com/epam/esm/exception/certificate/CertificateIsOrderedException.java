package com.epam.esm.exception.certificate;

/**
 * Throws when certificate, that was ordered, is tried to be deleted
 */
public class CertificateIsOrderedException extends RuntimeException {

    private long id;

    public CertificateIsOrderedException(long id) {

        this.id = id;
    }

    public CertificateIsOrderedException() {

        super();
    }

    public CertificateIsOrderedException(String message) {

        super(message);
    }

    public CertificateIsOrderedException(String message, Throwable cause) {

        super(message, cause);
    }

    public CertificateIsOrderedException(Throwable cause) {

        super(cause);
    }

    protected CertificateIsOrderedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public long getId() {

        return id;
    }
}
