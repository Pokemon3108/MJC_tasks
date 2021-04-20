package com.epam.esm.exception;


/**
 * NoCertificateException is thrown when required certificate doesn't exist
 */
public class NoCertificateException extends RuntimeException {

    private long certificateId;

    /**
     * Instantiates a new No certificate exception with certificate id
     *
     * @param id the id of certificate
     */
    public NoCertificateException(long id) {

        this.certificateId = id;
    }

    /**
     * {@inheritDoc}
     */
    public NoCertificateException() {

        super();
    }

    /**
     * {@inheritDoc}
     */
    public NoCertificateException(String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public NoCertificateException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public NoCertificateException(Throwable cause) {

        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    protected NoCertificateException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * {@inheritDoc}
     */
    public long getCertificateId() {

        return certificateId;
    }
}
