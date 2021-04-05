package com.epam.esm.error;

public enum ErrorCode {

    NO_CERTIFICATE(40401);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
