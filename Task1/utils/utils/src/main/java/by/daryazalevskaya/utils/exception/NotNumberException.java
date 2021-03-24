package by.daryazalevskaya.utils.exception;

public class NotNumberException extends RuntimeException {
    public NotNumberException() {
        super();
    }

    public NotNumberException(String message) {
        super(message);
    }

    public NotNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotNumberException(Throwable cause) {
        super(cause);
    }

    protected NotNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

