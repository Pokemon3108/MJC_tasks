package com.epam.esm.exception;

/**
 * Throws when refresh token expires or not exists
 */
public class RefreshTokenException extends RuntimeException {

    private String token;

    public RefreshTokenException(String token) {

        this.token = token;
    }

    public RefreshTokenException(String message, String token) {

        super(message);
        this.token = token;
    }

    public RefreshTokenException(String message, Throwable cause, String token) {

        super(message, cause);
        this.token = token;
    }

    public RefreshTokenException(Throwable cause, String token) {

        super(cause);
        this.token = token;
    }

    public RefreshTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            String token) {

        super(message, cause, enableSuppression, writableStackTrace);
        this.token = token;
    }

    public String getToken() {

        return token;
    }
}
