package com.epam.esm.exception.user;

/**
 * Throws when certificates in user's order has no tags
 */
public class UsersOrderHasNoTags extends RuntimeException {

    private long id;

    public UsersOrderHasNoTags(long id) {

        this.id = id;
    }

    public UsersOrderHasNoTags() {

    }

    public UsersOrderHasNoTags(String message) {

        super(message);
    }

    public UsersOrderHasNoTags(String message, Throwable cause) {

        super(message, cause);
    }

    public UsersOrderHasNoTags(Throwable cause) {

        super(cause);
    }

    public UsersOrderHasNoTags(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public long getId() {

        return id;
    }
}
