package com.epam.esm.error;

/**
 * Codes for custom errors. Have 5-digit value. <br>
 * Example: 40401. 404 - HTTP status code, 0 - entity code, 1 - error number.<br>
 * Entity code differs for entities. 0-common, 1 - certificate, 2 - tag, 3-order, 4 - user
 */
public enum ErrorCode {

    NO_ID(40401),
    NO_PAGE(40402),
    BASE_ERROR(40000),
    UNSUPPORTED_MEDIA_TYPE(41501),
    METHOD_NOT_ALLOWED(40501),
    MAX_SIZE_LIMIT(40001),
    FORBIDDEN(40300),
    NO_AUTHENTICATION(40100),
    JWT_EXPIRED(40301),

    NO_CERTIFICATE(40411),
    NOT_FULL_CERTIFICATE(40012),
    DUPLICATE_CERTIFICATE_NAME(40013),
    CERTIFICATE_IS_ORDERED(40014),

    NO_TAG(40421),
    DUPLICATE_TAG_NAME(40022),

    CREATION_ORDER(40031),
    NO_ORDER(40432),

    NO_USER_WITH_ID(40441),
    NO_USERS_IN_STORAGE(40442),
    NO_TAGS_IN_USER_ORDER(40443),
    NO_USERNAME(40444),
    DUPLICATE_USERNAME(40044),
    EXPIRED_REFRESH_TOKEN(40341),
    INVALID_USER(40145);

    private int code;

    ErrorCode(int code) {

        this.code = code;
    }

    public int getCode() {

        return code;
    }
}
