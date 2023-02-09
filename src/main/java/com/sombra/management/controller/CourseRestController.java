package com.sombra.management.controller;

import com.sombra.management.dto.CourseDTO;
import com.sombra.management.dto.CourseResDTO;
import com.sombra.management.dto.UserDTO;
import com.sombra.management.dto.RegisterUserToCourseDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/course")
public class CourseRestController {

    private final CourseService courseService;

//    [BUSINESS] The instructor should be able to see list of his courses; | ADMIN, INSTRUCTOR, STUDENT
//    [BUSINESS] The student should be able to see his courses; (security in service)
    @GetMapping(value = "/user/{userId}/courses")
    public ResponseEntity<Set<CourseResDTO>> getCoursesByUserId(@PathVariable final Long userId) {
        return ResponseEntity.ok().body(courseService.getCoursesByUserId(userId));
    }

//    [BUSINESS] The instructor should be able to see list of students per course;
    @GetMapping(value = "/{courseId}/students")
    public ResponseEntity<Set<UserDTO>> getStudentsPerCourse(@PathVariable final Long courseId) {
        return ResponseEntity.ok().body(courseService.getStudentsByCourseId(courseId));
    }

    //    [BUSINESS] Student can take up to 5 courses at the same time; | ADMIN / USER
    @PostMapping(value = "/register/user")
    public ResponseEntity<RegisterUserToCourseDTO> registerUserToCourse(@RequestBody final RegisterUserToCourseDTO userToCourseDTO) {
        return ResponseEntity.ok().body(courseService.registerUserToCourse(userToCourseDTO));
    }

//    [BUSINESS] Each course should have at least one instructor; | ADMIN / INSTRUCTOR
//    [BUSINESS] Each course contains at least 5 lessons;
    @PostMapping(value = "/create")
    public ResponseEntity<CourseDTO> createNewCourse(@RequestBody final CourseDTO courseDTO) {
        return ResponseEntity.ok().body(courseService.addNewCourse(courseDTO));
    }

//    [BUSINESS] Admin should be able to assign instructor for the course; | ADMIN
    @PostMapping(value = "/assign/instructor")
    public ResponseEntity<UserCourseDTO> assignInstructorToCourse(@RequestBody final UserCourseDTO userCourseDTO) {
        return ResponseEntity.ok().body(courseService.assignInstructorToCourse(userCourseDTO));
    }

}
