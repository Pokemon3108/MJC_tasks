package com.epam.esm.exception;

public class DuplicateTagException extends RuntimeException {

    private String name;

    public DuplicateTagException(String name) {

        this.name = name;
    }

    public DuplicateTagException(String message, Throwable cause, String name) {

        super(message, cause);
        this.name = name;
    }

    public DuplicateTagException(Throwable cause, String name) {

        super(cause);
        this.name = name;
    }

    public DuplicateTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            String name) {

        super(message, cause, enableSuppression, writableStackTrace);
        this.name = name;
    }

    public String getName() {

        return name;
    }
}
