package com.sombra.management.entity.enumeration;

import java.util.Arrays;

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
                .findFirst().orElseThrow(() -> {throw new IllegalArgumentException();});
    }
}
