package com.sombra.management.service.impl;

import com.sombra.management.dto.StudentFeedbackDTO;
import com.sombra.management.entity.CourseGraduationEntity;
import com.sombra.management.entity.FeedbackEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.FeedbackRepository;
import com.sombra.management.service.CourseGraduationService;
import com.sombra.management.service.FeedbackService;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final CourseGraduationService courseGraduationService;
    private final ModelMapper modelMapper;

    @Override
    public StudentFeedbackDTO addFeedbackForCourseGraduation(final StudentFeedbackDTO studentFeedbackDTO) {
        final var feedbackEntity = initializeFeedbackEntity(studentFeedbackDTO);
        feedbackRepository.save(feedbackEntity);
        return studentFeedbackDTO;
    }

    private FeedbackEntity initializeFeedbackEntity(final StudentFeedbackDTO studentFeedbackDTO) {
        final CourseGraduationEntity courseGraduationEntity = courseGraduationService
                .getCourseGraduationById(studentFeedbackDTO.getCourseGraduationId());
        final UserEntity userEntity = userService.findUserEntityByUserId(studentFeedbackDTO.getInstructorId());

        final FeedbackEntity feedbackEntity = modelMapper.map(studentFeedbackDTO, FeedbackEntity.class);
        feedbackEntity.setCourseGraduation(courseGraduationEntity);
        feedbackEntity.setDate(LocalDateTime.now());
        feedbackEntity.setInstructor(userEntity);
        return feedbackEntity;
    }
}
