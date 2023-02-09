package com.sombra.management.exception.code;

import org.springframework.http.HttpStatus;

public enum ServiceErrorCode implements ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value()),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value()),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value());


    private final int httpStatusCode;

    ServiceErrorCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
