package com.sombra.management.controller;

import com.sombra.management.dto.CourseGraduationDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.entity.enumeration.CourseGraduationStatus;
import com.sombra.management.service.CourseGraduationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseGraduationRestControllerTest {

    private CourseGraduationService courseGraduationService;
    private CourseGraduationRestController courseGraduationRestController;

    @BeforeEach
    void setUp() {
        courseGraduationService = mock(CourseGraduationService.class);
        courseGraduationRestController = new CourseGraduationRestController(courseGraduationService);
    }

    @Test
    void graduateCourse() {
        final CourseGraduationDTO expected = new CourseGraduationDTO(1L, BigDecimal.ONE, CourseGraduationStatus.PASSED);
        when(courseGraduationService.graduateCourse(any())).thenReturn(expected);

        final UserCourseDTO userCourseDTO = new UserCourseDTO(1L, 1L);
        final ResponseEntity<CourseGraduationDTO> actual = courseGraduationRestController.graduateCourse(userCourseDTO);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected.getId(), Objects.requireNonNull(actual.getBody()).getId());
        assertEquals(expected.getFinalMark(), Objects.requireNonNull(actual.getBody()).getFinalMark());
        assertEquals(expected.getStatus(), Objects.requireNonNull(actual.getBody()).getStatus());

    }
}