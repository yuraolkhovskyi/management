package com.sombra.management.service.impl;

import com.sombra.management.dto.MarkDTO;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.entity.MarkEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.MarkRepository;
import com.sombra.management.service.LessonService;
import com.sombra.management.service.MarkService;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final UserService userService;
    private final LessonService lessonService;

    @Override
    public MarkDTO putMarkByLesson(final MarkDTO markDTO) {
        final UserEntity student = userService.findUserEntityByUserId(markDTO.getStudentId());
        final UserEntity instructor = userService.findUserEntityByUserId(markDTO.getInstructorId());
        final LessonEntity lessonEntity = lessonService.findLessonEntityById(markDTO.getLessonId());

        final MarkEntity markEntity = new MarkEntity();

        markEntity.setMark(markDTO.getMark());
        markEntity.setUser(student);
        markEntity.setLesson(lessonEntity);
        markEntity.setInstructor(instructor);
        markEntity.setDate(LocalDateTime.now());

        markRepository.save(markEntity);

        return markDTO;
    }


}
