package com.epam.esm.exception.certificate;


/**
 * NotFullCertificateException is thrown when certificate has empty required fields
 */
public class IllegalCertificatePropertiesException extends RuntimeException {

    /**
     * Instantiates a new Not full certificate exception.
     */
    public IllegalCertificatePropertiesException() {

    }

    /**
     * {@inheritDoc}
     */
    public IllegalCertificatePropertiesException(String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalCertificatePropertiesException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalCertificatePropertiesException(Throwable cause) {

        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalCertificatePropertiesException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
