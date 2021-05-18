package com.epam.esm.exception.certificate;

/**
 * DuplicateCertificateException when inserted certificate with already present name
 */
public class DuplicateCertificateException extends RuntimeException {

    private String name;

    public DuplicateCertificateException(String name) {

        this.name = name;
    }

    public DuplicateCertificateException() {

    }

    public DuplicateCertificateException(String message, Throwable cause) {

        super(message, cause);
    }

    public DuplicateCertificateException(Throwable cause) {

        super(cause);
    }

    public DuplicateCertificateException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getName() {

        return name;
    }
}
