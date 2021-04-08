package com.epam.esm.error;

/**
 * Codes for custom errors. Have 5-digit value. <br>
 * Example: 40401. 404 - HTTP status code, 0 - entity code, 1 - error number.<br>
 * Entity code differs for entities. 0 - certificate, 1 - tag, 2-common.
 */
public enum ErrorCode {

    NO_CERTIFICATE(40401),
    NOT_FULL_CERTIFICATE(40402),
    NO_TAG(40411),
    NO_ID(40421);

    private int code;

    ErrorCode(int code) {

        this.code = code;
    }

    public int getCode() {

        return code;
    }
}
