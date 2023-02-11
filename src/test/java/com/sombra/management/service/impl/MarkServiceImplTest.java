package com.sombra.management.service.impl;

import com.sombra.management.dto.CourseDTO;
import com.sombra.management.dto.LessonDTO;
import com.sombra.management.dto.MarkDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.entity.MarkEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.MarkRepository;
import com.sombra.management.service.CourseService;
import com.sombra.management.service.LessonService;
import com.sombra.management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkServiceImplTest {

    @Mock
    private MarkRepository markRepository;

    @Mock
    private UserService userService;

    @Mock
    private LessonService lessonService;

    @Mock
    private CourseService courseService;

    private MarkServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new MarkServiceImpl(markRepository, userService, lessonService, courseService);
    }
    @Test
    void putMarkByLesson_test1() {
        //given
        final MarkDTO markDTO = new MarkDTO(1, 2L, 3L, 4L);
        final var lessonEntity = new LessonEntity(1L, "test", null, null, null);
        when(userService.findUserEntityByUserId(anyLong())).thenReturn(new UserEntity());
        when(lessonService.findLessonEntityById(anyLong())).thenReturn(lessonEntity);

        //when
        final MarkDTO actual = underTest.putMarkByLesson(markDTO);

        //then
        verify(userService, times(2)).findUserEntityByUserId(any());
        verify(lessonService).findLessonEntityById(any());

        assertEquals(markDTO.getMark(), actual.getMark());
        assertEquals(markDTO.getLessonId(), actual.getLessonId());
        assertEquals(markDTO.getInstructorId(), actual.getInstructorId());
        assertEquals(markDTO.getStudentId(), actual.getStudentId());
    }

    @Test
    void calculateFinalMarkByStudentAndCourse_test1() {
        //given
        final Double expected = 5.0;
        final CourseDTO courseDTO =  mock(CourseDTO.class);
        final LessonDTO lessonDTO = new LessonDTO(1L, "test");
        when(courseDTO.getLessons()).thenReturn(Set.of(lessonDTO));

        when(courseService.findById(anyLong())).thenReturn(courseDTO);

        final MarkEntity markEntity = mock(MarkEntity.class);
        when(markEntity.getMark()).thenReturn(5);
        when(markRepository.findAllByStudentIdAndLessonId(anyLong(), anyLong())).thenReturn(Set.of(markEntity));

        final UserCourseDTO userCourseDTO = new UserCourseDTO(1L, 1L);
        //when
        final Double actual = underTest.calculateFinalMarkByStudentAndCourse(userCourseDTO);

        //then
        assertEquals(expected, actual);
    }
}