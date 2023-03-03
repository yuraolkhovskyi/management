package com.sombra.management.service.impl;

import com.sombra.management.dto.FileResDTO;
import com.sombra.management.entity.FileEntity;
import com.sombra.management.exception.SystemException;
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
import java.util.UUID;

import static com.sombra.management.exception.ErrorMessageConstants.BAD_REQUEST_ERROR_MESSAGE;
import static com.sombra.management.exception.code.ServiceErrorCode.BAD_REQUEST;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Override
    public UUID storeFile(final MultipartFile homeworkFile) throws IOException {
        return store(homeworkFile).getId();
    }

    @Override
    public FileEntity save(final MultipartFile homeworkFile) throws IOException {
        return store(homeworkFile);
    }

    @Override
    public FileResDTO downloadFile(final UUID fileId) {
        final FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> {
                    log.error("File with id {} doesn't exist", fileId);
                    throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
                });
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
