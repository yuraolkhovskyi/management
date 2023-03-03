package com.sombra.management.service.impl;

import com.sombra.management.dto.LessonDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.LessonRepository;
import com.sombra.management.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.sombra.management.exception.ErrorMessageConstants.BAD_REQUEST_ERROR_MESSAGE;
import static com.sombra.management.exception.code.ServiceErrorCode.BAD_REQUEST;

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

    @Override
    public LessonEntity findLessonEntityById(final Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> {
                    log.error("No lesson entity found by id {}", lessonId);
                    throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
                });
    }


    @Override
    public Set<LessonDTO> saveLessons(final Set<LessonDTO> lessons, final CourseEntity courseEntity) {
        for (final LessonDTO lesson : lessons) {
            final LessonEntity lessonToSave = new LessonEntity();
            lessonToSave.setTitle(lesson.getTitle());
            lessonToSave.setCourse(courseEntity);
            lesson.setId(lessonRepository.save(lessonToSave).getId());
        }
        return lessons;
    }


}
