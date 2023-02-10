package com.sombra.management.service.impl;

import com.sombra.management.dto.StudentFeedbackDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.CourseGraduationEntity;
import com.sombra.management.entity.FeedbackEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.FeedbackRepository;
import com.sombra.management.service.CourseGraduationService;
import com.sombra.management.service.CourseService;
import com.sombra.management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserService userService;

    @Mock
    private CourseGraduationService courseGraduationService;

    @Mock
    private ModelMapper modelMapper;

    private FeedbackServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new FeedbackServiceImpl(feedbackRepository, userService, courseGraduationService, modelMapper);
    }

    @Test
    void addFeedbackForCourseGraduation() {
        //given
        when(modelMapper.map(any(), any())).thenReturn(new FeedbackEntity());
        when(courseGraduationService.getCourseGraduationById(anyLong())).thenReturn(new CourseGraduationEntity());
        when(userService.findUserEntityByUserId(anyLong())).thenReturn(new UserEntity());
        when(feedbackRepository.save(any())).thenReturn(new FeedbackEntity());

        //when
        final StudentFeedbackDTO expected = new StudentFeedbackDTO("test", 1l, 1l);
        final StudentFeedbackDTO actual = underTest.addFeedbackForCourseGraduation(expected);

        //then
        verify(modelMapper).map(any(), any());
        verify(courseGraduationService).getCourseGraduationById(anyLong());
        verify(userService).findUserEntityByUserId(anyLong());
        verify(feedbackRepository).save(any());
        assertEquals(expected, actual);
    }
}