package com.sombra.management.entity.enumeration;

import com.sombra.management.exception.SystemException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.sombra.management.exception.ErrorMessageConstants.INTERNAL_SERVER_ERROR_MESSAGE;
import static com.sombra.management.exception.code.ServiceErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
public enum CourseGraduationStatus {

    PASSED(0),
    FAILED(1);

    private final int dbCode;

    CourseGraduationStatus(int dbCode) {
        this.dbCode = dbCode;
    }

    public int getDbCode() {
        return dbCode;
    }

    public static CourseGraduationStatus getByCode(final int dbCode) {
        return Arrays.stream(CourseGraduationStatus.values())
                .filter(status -> status.getDbCode() == dbCode)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No Course Graduation Status found by db code {}", dbCode);
                    throw new SystemException(INTERNAL_SERVER_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
                });
    }

}
