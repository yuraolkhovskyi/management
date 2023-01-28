package com.sombra.management.service;

import com.sombra.management.entity.CourseEntity;

import java.util.Set;

public interface CourseService {
    Set<CourseEntity> getCoursesByUserId(final Long userId);
}
