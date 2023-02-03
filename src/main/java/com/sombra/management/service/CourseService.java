package com.sombra.management.service;

import com.sombra.management.dto.CourseDTO;
import com.sombra.management.dto.CourseResDTO;
import com.sombra.management.dto.RegisterUserToCourseDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.entity.CourseEntity;

import java.util.Set;

public interface CourseService {

    Set<CourseResDTO> getCoursesByUserId(final Long userId);

    CourseDTO addNewCourse(final CourseDTO courseDto);

    RegisterUserToCourseDTO registerUserToCourse(final RegisterUserToCourseDTO userToCourseDTO);

    CourseDTO findById(final Long courseId);

    CourseEntity findCourseEntityById(final Long courseId);

    UserCourseDTO assignInstructorToCourse(final UserCourseDTO userCourseDTO);
}
