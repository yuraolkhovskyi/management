package com.sombra.management.exception.dto;

import com.sombra.management.exception.SystemException;
import com.sombra.management.exception.code.ErrorCode;
import lombok.Data;

@Data
public class ErrorDTO {

    private ErrorCode errorCode;
    private int httpStatusCode;
    private String message;

    public ErrorDTO(final SystemException systemException) {
        final ErrorCode errorObject = systemException.getErrorCode();
        this.errorCode = errorObject;
        this.httpStatusCode = errorObject.getHttpStatusCode();
        this.message = systemException.getMessage();
    }

}
