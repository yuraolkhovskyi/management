package com.sombra.management.service.impl;

import com.sombra.management.entity.CourseGraduationEntity;
import com.sombra.management.repository.CourseGraduationRepository;
import com.sombra.management.service.CourseGraduationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseGraduationServiceImpl implements CourseGraduationService {

    private final CourseGraduationRepository courseGraduationRepository;

    @Override
    public CourseGraduationEntity getCourseGraduationById(final Long courseGraduationId) {
        return courseGraduationRepository.findById(courseGraduationId)
                .orElseThrow(() -> {
                    throw new RuntimeException(String.format("Course Graduation with id {%d} doesn't exist", courseGraduationId));
                });
    }

}
