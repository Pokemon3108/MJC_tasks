package com.epam.esm.exception.order;

public class NoOrderException extends RuntimeException {

    private long orderId;

    public NoOrderException(long orderId) {

        this.orderId = orderId;
    }

    public NoOrderException(String message) {

        super(message);
    }

    public NoOrderException(String message, Throwable cause) {

        super(message, cause);
    }

    public NoOrderException(Throwable cause) {

        super(cause);
    }

    public NoOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public long getOrderId() {

        return orderId;
    }

}
