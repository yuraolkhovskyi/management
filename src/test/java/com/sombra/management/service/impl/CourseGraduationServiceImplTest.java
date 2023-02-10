package com.sombra.management.service.impl;

import com.sombra.management.dto.CourseGraduationDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.dto.UserDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.CourseGraduationEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.entity.enumeration.CourseGraduationStatus;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.CourseGraduationRepository;
import com.sombra.management.service.CourseGraduationService;
import com.sombra.management.service.CourseService;
import com.sombra.management.service.MarkService;
import com.sombra.management.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseGraduationServiceImplTest {

    @Mock
    private CourseGraduationRepository courseGraduationRepository;

    @Mock
    private MarkService markService;

    @Mock
    private UserService userService;

    @Mock
    private CourseService courseService;

    @Mock
    private ModelMapper modelMapper;


    private CourseGraduationServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new CourseGraduationServiceImpl(courseGraduationRepository,
                markService, userService, courseService, modelMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void getCourseGraduationById_test1() {
        //given
        final CourseGraduationEntity expected = new CourseGraduationEntity();
        when(courseGraduationRepository.findById(any())).thenReturn(Optional.of(expected));

        //when
        final long courseGraduationId = Long.MAX_VALUE;
        final CourseGraduationEntity actual = underTest.getCourseGraduationById(courseGraduationId);

        //then
        verify(courseGraduationRepository).findById(courseGraduationId);
        assertEquals(expected, actual);
    }

    @Test
    void getCourseGraduationById_test2() {
        //then
        Assertions.assertThrows(SystemException.class, () -> underTest.getCourseGraduationById(any()));
    }

    @Test
    void graduateCourse_test1() {
        //given
        final Double mark = 5.0;
        final long userId = 1L;
        final long courseId = 1L;
        final CourseGraduationDTO expected = new CourseGraduationDTO(userId, BigDecimal.valueOf(5.0),
                CourseGraduationStatus.PASSED);

        when(markService.calculateFinalMarkByStudentAndCourse(any())).thenReturn(mark);
        when(userService.findUserEntityByUserId(userId)).thenReturn(new UserEntity());
        when(courseService.findCourseEntityById(anyLong())).thenReturn(new CourseEntity());
        when(courseGraduationRepository.save(any())).thenReturn(new CourseGraduationEntity());
        when(modelMapper.map(any(), any())).thenReturn(expected);

        //when
        final UserCourseDTO userCourseDTO = new UserCourseDTO(userId, courseId);
        final CourseGraduationDTO actual = underTest.graduateCourse(userCourseDTO);

        //then
        verify(markService).calculateFinalMarkByStudentAndCourse(any());
        verify(userService).findUserEntityByUserId(userId);
        verify(courseService).findCourseEntityById(any());
        verify(courseGraduationRepository).save(any());
        verify(modelMapper).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    void graduateCourse_test2() {
        //given
        final Double mark = 2.0;
        final long userId = 1L;
        final long courseId = 1L;
        final CourseGraduationDTO expected = new CourseGraduationDTO(userId, BigDecimal.valueOf(5.0),
                CourseGraduationStatus.FAILED);

        when(markService.calculateFinalMarkByStudentAndCourse(any())).thenReturn(mark);
        when(userService.findUserEntityByUserId(userId)).thenReturn(new UserEntity());
        when(courseService.findCourseEntityById(anyLong())).thenReturn(new CourseEntity());
        when(courseGraduationRepository.save(any())).thenReturn(new CourseGraduationEntity());
        when(modelMapper.map(any(), any())).thenReturn(expected);

        //when
        final UserCourseDTO userCourseDTO = new UserCourseDTO(userId, courseId);
        final CourseGraduationDTO actual = underTest.graduateCourse(userCourseDTO);

        //then
        verify(markService).calculateFinalMarkByStudentAndCourse(any());
        verify(userService).findUserEntityByUserId(userId);
        verify(courseService).findCourseEntityById(any());
        verify(courseGraduationRepository).save(any());
        verify(modelMapper).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    void calculateGraduateCourseStatusByFinalMark_test1() {
        //given
        final double finalMark = 4.0;
        final CourseGraduationStatus expected = CourseGraduationStatus.PASSED;

        //when
        final CourseGraduationStatus actual = underTest
                .calculateGraduateCourseStatusByFinalMark((int) finalMark);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void calculateGraduateCourseStatusByFinalMark_test2() {
        //given
        final double finalMark = 3.5;
        final CourseGraduationStatus expected = CourseGraduationStatus.FAILED;

        //when
        final CourseGraduationStatus actual = underTest
                .calculateGraduateCourseStatusByFinalMark((int) finalMark);

        //then
        assertEquals(expected, actual);
    }
}