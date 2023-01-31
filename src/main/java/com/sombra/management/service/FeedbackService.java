package com.sombra.management.service;

import com.sombra.management.dto.StudentFeedbackDTO;

public interface FeedbackService {

    StudentFeedbackDTO addFeedbackForCourseGraduation(final StudentFeedbackDTO studentFeedbackDTO);

}
