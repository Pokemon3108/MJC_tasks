package com.epam.esm.exception.certificate;


/**
 * NotFullCertificateException is thrown when certificate has empty required fields
 */
public class IllegalCertificateProperties extends RuntimeException {

    /**
     * Instantiates a new Not full certificate exception.
     */
    public IllegalCertificateProperties() {

    }

    /**
     * {@inheritDoc}
     */
    public IllegalCertificateProperties(String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalCertificateProperties(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalCertificateProperties(Throwable cause) {

        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalCertificateProperties(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
