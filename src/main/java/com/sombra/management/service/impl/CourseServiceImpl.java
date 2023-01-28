package com.sombra.management.service.impl;

import com.sombra.management.entity.CourseEntity;
import com.sombra.management.repository.CourseRepository;
import com.sombra.management.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Set<CourseEntity> getCoursesByUserId(final Long userId) {
        final Set<CourseEntity> courseEntitiesByUserId = courseRepository.getCourseEntitiesByUserId(userId);
        return courseEntitiesByUserId;
    }
}
