package com.sombra.management.controller;

import com.sombra.management.dto.StudentFeedbackDTO;
import com.sombra.management.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeedbackRestControllerTest {

    private FeedbackService feedbackService;
    private FeedbackRestController feedbackRestController;

    @BeforeEach
    void setUp() {
        feedbackService = mock(FeedbackService.class);
        feedbackRestController = new FeedbackRestController(feedbackService);
    }

    @Test
    void addStudentFeedbackForCourseGraduation() {
        final StudentFeedbackDTO expected = new StudentFeedbackDTO("message", 1L, 1L);
        when(feedbackService.addFeedbackForCourseGraduation(any())).thenReturn(expected);

        final ResponseEntity<StudentFeedbackDTO> actual = feedbackRestController
                .addStudentFeedbackForCourseGraduation(expected);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected.getCourseGraduationId(), Objects.requireNonNull(actual.getBody()).getCourseGraduationId());
        assertEquals(expected.getFeedbackMessage(), Objects.requireNonNull(actual.getBody()).getFeedbackMessage());
        assertEquals(expected.getInstructorId(), Objects.requireNonNull(actual.getBody()).getInstructorId());

    }
}