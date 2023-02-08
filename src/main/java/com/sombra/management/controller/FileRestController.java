package com.sombra.management.controller;

import com.sombra.management.dto.FileResDTO;
import com.sombra.management.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/file")
public class FileRestController {

    private final FileStorageService fileStorageService;

//    [BUSINESS] The student should be able to upload a text file with homework; | ADMIN / STUDENT
    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok().body(fileStorageService.storeFile(multipartFile));
    }

//     | ADMIN / STUDENT
    @GetMapping(value = "/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable final String fileId) {
        final FileResDTO fileResDTO = fileStorageService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileResDTO.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResDTO.getFileName())
                .body(new ByteArrayResource(fileResDTO.getFile()));
    }


}
