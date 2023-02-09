package com.sombra.management.entity.enumeration;

import com.sombra.management.exception.SystemException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.sombra.management.exception.ErrorMessageConstants.INTERNAL_SERVER_ERROR_MESSAGE;
import static com.sombra.management.exception.code.ServiceErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
public enum UserRole {

    STUDENT(0),
    INSTRUCTOR(1),
    ADMIN(2);

    private final int dbCode;

    UserRole(int dbCode) {
        this.dbCode = dbCode;
    }

    public int getDbCode() {
        return dbCode;
    }

    public static UserRole getByCode(final int dbCode) {
        return Arrays.stream(UserRole.values())
                .filter(e -> e.getDbCode() == dbCode)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No User Role found by db code {}", dbCode);
                    throw new SystemException(INTERNAL_SERVER_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
                });
    }
}
