package com.sombra.management.service;

import com.sombra.management.dto.LessonDTO;
import com.sombra.management.entity.LessonEntity;

import java.util.Set;

public interface LessonService {

    Set<LessonDTO> getLessonsByCourseId(final Long courseId);

    LessonEntity findLessonEntityById(final Long lessonId);

}
