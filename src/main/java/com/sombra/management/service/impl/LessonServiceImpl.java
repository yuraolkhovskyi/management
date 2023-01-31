package com.sombra.management.service.impl;

import com.sombra.management.dto.LessonDTO;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.repository.LessonRepository;
import com.sombra.management.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    @Override
    public Set<LessonDTO> getLessonsByCourseId(final Long courseId) {
        return lessonRepository.getLessonEntitiesByCourse_Id(courseId).stream()
                .map(e -> modelMapper.map(e, LessonDTO.class))
                .collect(Collectors.toSet());
    }


}
