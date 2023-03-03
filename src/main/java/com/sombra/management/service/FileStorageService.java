package com.sombra.management.service;

import com.sombra.management.dto.FileResDTO;
import com.sombra.management.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileStorageService {

    UUID storeFile(final MultipartFile homeworkFile) throws IOException;

    FileEntity save(final MultipartFile homeworkFile) throws IOException;

    FileResDTO downloadFile(final UUID fileId);
}
