package com.sombra.management.controller;

import com.sombra.management.dto.MarkDTO;
import com.sombra.management.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/mark")
public class MarkRestController {

    private final MarkService markService;

    @PostMapping
    public ResponseEntity<MarkDTO> putMarkForLesson(@RequestBody final MarkDTO markDTO) {
        return ResponseEntity.ok().body(markService.putMarkByLesson(markDTO));
    }

}
