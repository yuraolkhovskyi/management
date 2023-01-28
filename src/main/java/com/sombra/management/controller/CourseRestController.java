package com.sombra.management.controller;

import com.sombra.management.dto.CourseResDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/course")
public class CourseRestController {

    private final CourseService courseService;

    // FIXME: 28.01.2023 restrict access for INSTRUCTOR only
    @GetMapping(value = "/user/{userId}/courses")
    public ResponseEntity<Set<CourseResDTO>> getCoursesByInstructorId(@PathVariable final Long userId) {
        return ResponseEntity.ok().body(courseService.getCoursesByUserId(userId));
    }


}
