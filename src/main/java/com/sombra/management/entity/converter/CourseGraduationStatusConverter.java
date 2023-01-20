package com.sombra.management.entity.converter;

import com.sombra.management.entity.enumeration.CourseGraduationStatus;
import jakarta.persistence.AttributeConverter;

public class CourseGraduationStatusConverter implements AttributeConverter<CourseGraduationStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final CourseGraduationStatus attribute) {
        return attribute.getDbCode();
    }

    @Override
    public CourseGraduationStatus convertToEntityAttribute(final Integer dbCode) {
        return CourseGraduationStatus.getByCode(dbCode);
    }
}
