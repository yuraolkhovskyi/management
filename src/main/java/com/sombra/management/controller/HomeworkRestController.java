package com.sombra.management.controller;

import com.sombra.management.dto.HomeworkResDTO;
import com.sombra.management.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/homework")
public class HomeworkRestController {

    private final HomeworkService homeworkService;

    @PostMapping(value = "/lesson/{lessonId}/user/{userId}")
    public ResponseEntity<HomeworkResDTO> getLessonsByCourseId(
            @PathVariable final Long lessonId,
            @PathVariable final Long userId,
            @RequestParam("file") final MultipartFile homeworkFile) throws IOException {
        return ResponseEntity.ok().body(homeworkService.uploadHomework(homeworkFile, lessonId, userId));
    }

}
