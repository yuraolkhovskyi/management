package com.sombra.management.exception;

import com.sombra.management.exception.code.ErrorCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.util.Strings;

@Getter
@ToString
public class SystemException extends RuntimeException {

    private final ErrorCode errorCode;

    public SystemException(final String message, final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        final String message = super.getMessage();
        return Strings.isBlank(message) ? null : message;
    }
}
