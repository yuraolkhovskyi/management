package com.sombra.management.controller;

import com.sombra.management.dto.StudentFeedbackDTO;
import com.sombra.management.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/feedback")
public class FeedbackRestController {

    private final FeedbackService feedbackService;

    // FIXME: 30.01.2023 limit access to INSTRUCTOR
//    [BUSINESS] The instructor should be able to give student final feedback for the course;
    @PostMapping
    public ResponseEntity<StudentFeedbackDTO> addStudentFeedbackForCourseGraduation(
            @RequestBody final StudentFeedbackDTO studentFeedbackDTO) {
        return ResponseEntity.ok().body(feedbackService.addFeedbackForCourseGraduation(studentFeedbackDTO));
    }


}
