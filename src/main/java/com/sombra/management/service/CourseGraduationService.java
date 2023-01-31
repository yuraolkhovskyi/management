package com.sombra.management.service;

import com.sombra.management.dto.CourseGraduationDTO;
import com.sombra.management.entity.CourseGraduationEntity;

public interface CourseGraduationService {

    CourseGraduationEntity getCourseGraduationById(final Long courseGraduationId);

}
