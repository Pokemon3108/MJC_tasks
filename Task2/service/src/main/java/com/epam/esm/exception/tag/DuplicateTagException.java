package com.epam.esm.exception.tag;

/**
 * DuplicateTagException when inserted tag with already present name
 */
public class DuplicateTagException extends RuntimeException {

    private String name;

    /**
     * Instantiates a new Duplicate tag exception.
     *
     * @param name of tag
     */
    public DuplicateTagException(String name) {

        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public DuplicateTagException(String message, Throwable cause, String name) {

        super(message, cause);
        this.name = name;
    }

    /**
     * {@inheritDoc}
     *
     * @param cause the cause
     * @param name  the name
     */
    public DuplicateTagException(Throwable cause, String name) {

        super(cause);
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public DuplicateTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            String name) {

        super(message, cause, enableSuppression, writableStackTrace);
        this.name = name;
    }

    /**
     * Gets name of tag
     *
     * @return the name of tag
     */
    public String getName() {

        return name;
    }
}
