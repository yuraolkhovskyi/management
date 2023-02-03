package com.sombra.management.service.impl;

import com.sombra.management.dto.*;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.entity.MarkEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.MarkRepository;
import com.sombra.management.service.CourseService;
import com.sombra.management.service.LessonService;
import com.sombra.management.service.MarkService;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final UserService userService;
    private final LessonService lessonService;
    private final CourseService courseService;

    @Override
    public MarkDTO putMarkByLesson(final MarkDTO markDTO) {
        final UserEntity student = userService.findUserEntityByUserId(markDTO.getStudentId());
        final UserEntity instructor = userService.findUserEntityByUserId(markDTO.getInstructorId());
        final LessonEntity lessonEntity = lessonService.findLessonEntityById(markDTO.getLessonId());

        final MarkEntity markEntity = new MarkEntity();

        markEntity.setMark(markDTO.getMark());
        markEntity.setStudent(student);
        markEntity.setLesson(lessonEntity);
        markEntity.setInstructor(instructor);
        markEntity.setDate(LocalDateTime.now());

        markRepository.save(markEntity);

        return markDTO;
    }

    @Override
    public Double calculateFinalMarkByStudentAndCourse(final UserCourseDTO userCourseDTO) {

        final Set<LessonDTO> lessons = courseService.findById(userCourseDTO.getCourseId()).getLessons();

        List<Integer> marks = new ArrayList<>();
        for (final LessonDTO lesson : lessons) {
            final Set<MarkEntity> marksByStudentAndLessonIds = markRepository // student can get multiple marks per lesson
                    .findAllByStudentIdAndLessonId(userCourseDTO.getUserId(), lesson.getId());
            marksByStudentAndLessonIds.stream().map(MarkEntity::getMark).forEach(marks::add);
        }

        return marks.stream().mapToDouble(a -> a).sum() / marks.size();
    }


}
