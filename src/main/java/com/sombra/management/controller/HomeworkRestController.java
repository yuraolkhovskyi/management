package com.sombra.management.controller;

import com.sombra.management.dto.HomeworkResDTO;
import com.sombra.management.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/homework")
public class HomeworkRestController {

    private final HomeworkService homeworkService;

//    [BUSINESS] The student should be able to upload a text file with homework;
    @PostMapping(value = "/lesson/{lessonId}/user/{userId}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HomeworkResDTO> uploadHomework(
            @PathVariable final Long lessonId,
            @PathVariable final Long userId,
            @RequestParam("file") final MultipartFile homeworkFile) throws IOException {
        return ResponseEntity.ok().body(homeworkService.uploadHomework(homeworkFile, lessonId, userId));
    }

}
