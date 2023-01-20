package com.sombra.management.entity.converter;

import com.sombra.management.entity.enumeration.UserRole;
import jakarta.persistence.AttributeConverter;

public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(final UserRole userRole) {
        return userRole.getDbCode();
    }

    @Override
    public UserRole convertToEntityAttribute(final Integer dbCode) {
        return UserRole.getByCode(dbCode);
    }
}
