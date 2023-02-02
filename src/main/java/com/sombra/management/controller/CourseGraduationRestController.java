package com.sombra.management.controller;

import com.sombra.management.dto.*;
import com.sombra.management.service.CourseGraduationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/course-graduation")
public class CourseGraduationRestController {

    private final CourseGraduationService courseGraduationService;

    @PostMapping(value = "/graduate")
    public ResponseEntity<CourseGraduationDTO> createNewCourse(@RequestBody final StudentCourseDTO studentCourseDTO) {
        return ResponseEntity.ok().body(courseGraduationService.graduateCourse(studentCourseDTO));
    }

}
