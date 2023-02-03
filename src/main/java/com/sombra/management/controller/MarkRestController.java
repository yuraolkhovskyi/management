package com.sombra.management.controller;

import com.sombra.management.dto.MarkDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/mark")
public class MarkRestController {

    private final MarkService markService;

//    [BUSINESS] The instructor should be able to put a mark for a student for each lesson;
    @PostMapping
    public ResponseEntity<MarkDTO> putMarkForLesson(@RequestBody final MarkDTO markDTO) {
        return ResponseEntity.ok().body(markService.putMarkByLesson(markDTO));
    }

//    [BUSINESS] The final mark for course is average by lessons;
    @PostMapping(value = "/calculate")
    public ResponseEntity<Double> calculateFinalMark(@RequestBody final UserCourseDTO userCourseDTO) {
        return ResponseEntity.ok().body(markService.calculateFinalMarkByStudentAndCourse(userCourseDTO));
    }

}
