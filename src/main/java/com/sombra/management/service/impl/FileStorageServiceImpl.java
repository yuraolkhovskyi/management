package com.sombra.management.service.impl;

import com.sombra.management.dto.FileResDTO;
import com.sombra.management.entity.FileEntity;
import com.sombra.management.repository.FileRepository;
import com.sombra.management.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Override
    public String storeFile(final MultipartFile homeworkFile) throws IOException {
        return store(homeworkFile).getId();
    }

    @Override
    public FileEntity save(final MultipartFile homeworkFile) throws IOException {
        return store(homeworkFile);
    }

    @Override
    public FileResDTO downloadFile(final String fileId) {
        final FileEntity fileEntity = fileRepository.findById(fileId).orElseThrow();
        return modelMapper.map(fileEntity, FileResDTO.class);
    }

    private FileEntity store(final MultipartFile homeworkFile) throws IOException {
        final String fileName = StringUtils
                .cleanPath(Objects.requireNonNull(homeworkFile.getOriginalFilename()));

        final FileEntity fileEntity = new FileEntity();

        fileEntity.setFileName(fileName);
        fileEntity.setFileType(homeworkFile.getContentType());
        fileEntity.setFile(homeworkFile.getBytes());

        return fileRepository.save(fileEntity);
    }


}
