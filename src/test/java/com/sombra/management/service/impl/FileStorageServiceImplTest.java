package com.sombra.management.service.impl;

import com.sombra.management.dto.FileResDTO;
import com.sombra.management.entity.FileEntity;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileStorageServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private ModelMapper modelMapper;

    private FileStorageServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new FileStorageServiceImpl(fileRepository, modelMapper);
    }

    @Test
    void storeFile_test1() throws IOException {
        //given
        final byte[] file = new byte[]{};
        final MultipartFile multipartFile = new MockMultipartFile("test", file);

        final UUID expectedId = UUID.randomUUID();
        final FileEntity fileEntity = new FileEntity(expectedId, "test", "test", null, null);
        when(fileRepository.save(any())).thenReturn(fileEntity);

        //when
        final UUID actualId = underTest.storeFile(multipartFile);

        //then
        verify(fileRepository).save(any());
        assertEquals(expectedId, actualId);
    }

    @Test
    void save_test1() throws IOException {
        //given
        final byte[] file = new byte[]{};
        final MultipartFile multipartFile = new MockMultipartFile("test", file);

        final UUID expectedId = UUID.randomUUID();
        final FileEntity fileEntity = new FileEntity(expectedId, "test", "test", null, null);
        when(fileRepository.save(any())).thenReturn(fileEntity);

        //when
        final FileEntity actual = underTest.save(multipartFile);

        //then
        verify(fileRepository).save(any());
        assertEquals(expectedId, actual.getId());
    }

    @Test
    void downloadFile_test1() {
        //given
        final FileResDTO expected = new FileResDTO();

        final UUID expectedId = UUID.randomUUID();
        final FileEntity fileEntity = new FileEntity(expectedId, "test", "test", null, null);
        when(fileRepository.findById(any())).thenReturn(Optional.of(fileEntity));
        when(modelMapper.map(any(), any())).thenReturn(expected);

        //when
        final FileResDTO actual = underTest.downloadFile(expectedId);

        //then
        verify(fileRepository).findById(any());
        assertEquals(expected, actual);
    }

    @Test
    void downloadFile_test2() {
        Assertions.assertThrows(SystemException.class,
                () -> underTest.downloadFile(any()));

    }
}