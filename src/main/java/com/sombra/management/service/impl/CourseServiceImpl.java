package com.sombra.management.service.impl;

import com.sombra.management.repository.CourseRepository;
import com.sombra.management.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

}
