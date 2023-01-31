package com.sombra.management.controller;

import com.sombra.management.dto.LessonDTO;
import com.sombra.management.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/lesson")
public class LessonRestController {

    private final LessonService lessonService;

    // FIXME: 30.01.2023 limit access to student
//    [BUSINESS] The student should be able to see list of lessons per course with all related information;
    @GetMapping(value = "/course/{courseId}/lessons")
    public ResponseEntity<Set<LessonDTO>> getLessonsByCourseId(@PathVariable final Long courseId) {
        return ResponseEntity.ok().body(lessonService.getLessonsByCourseId(courseId));
    }


}
