package com.sombra.management.service;

import com.sombra.management.dto.CourseResDTO;

import java.util.Set;

public interface CourseService {
    Set<CourseResDTO> getCoursesByUserId(final Long userId);
}
