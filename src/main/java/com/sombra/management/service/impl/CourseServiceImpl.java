package com.sombra.management.service.impl;

import com.sombra.management.dto.CourseResDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.repository.CourseRepository;
import com.sombra.management.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public Set<CourseResDTO> getCoursesByUserId(final Long userId) {
        return courseRepository.getCourseEntitiesByUserId(userId).stream()
                .map(e -> modelMapper.map(e, CourseResDTO.class))
                .collect(Collectors.toSet());

    }
}
