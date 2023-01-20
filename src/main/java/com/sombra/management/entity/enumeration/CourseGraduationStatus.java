package com.sombra.management.entity.enumeration;

import java.util.Arrays;

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
                .filter(e -> e.getDbCode() == dbCode)
                .findFirst()
                .orElseThrow(() -> {
                    throw new IllegalArgumentException();
                });
    }

}
