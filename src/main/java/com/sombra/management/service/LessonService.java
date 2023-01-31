package com.sombra.management.service;

import com.sombra.management.dto.LessonDTO;

import java.util.Set;

public interface LessonService {

    Set<LessonDTO> getLessonsByCourseId(final Long courseId);

}
