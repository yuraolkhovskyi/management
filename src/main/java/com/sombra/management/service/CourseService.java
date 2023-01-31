package com.sombra.management.service;

import com.sombra.management.dto.CourseDTO;
import com.sombra.management.dto.CourseResDTO;
import com.sombra.management.dto.RegisterUserToCourseDTO;

import java.util.Set;

public interface CourseService {
    Set<CourseResDTO> getCoursesByUserId(final Long userId);

    CourseDTO addNewCourse(final CourseDTO courseDto);

    RegisterUserToCourseDTO registerUserToCourse(final RegisterUserToCourseDTO userToCourseDTO);
}
