package com.sombra.management.service.impl;

import com.sombra.management.entity.FileEntity;
import com.sombra.management.entity.HomeworkEntity;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.HomeworkRepository;
import com.sombra.management.service.FileStorageService;
import com.sombra.management.service.LessonService;
import com.sombra.management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomeworkServiceImplTest {

    @Mock
    private HomeworkRepository homeworkRepository;

    @Mock
    private UserService userService;

    @Mock
    private LessonService lessonService;

    @Mock
    private FileStorageService fileStorageService;

    private HomeworkServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new HomeworkServiceImpl(homeworkRepository, userService, lessonService, fileStorageService);
    }

    @Test
    void uploadHomework_test1() throws IOException {
        //given
        final byte[] file = new byte[]{};
        final MultipartFile multipartFile = new MockMultipartFile("test", file);
        final Long lessonId = 1L;
        final Long userId = 1L;

        when(lessonService.findLessonEntityById(anyLong())).thenReturn(new LessonEntity());
        when(userService.findUserEntityByUserId(anyLong())).thenReturn(new UserEntity());
        when(fileStorageService.save(any())).thenReturn(new FileEntity());
        when(homeworkRepository.save(any())).thenReturn(new HomeworkEntity());

        //when
        underTest.uploadHomework(multipartFile, lessonId, userId);

        //then
        verify(lessonService).findLessonEntityById(any());
        verify(userService).findUserEntityByUserId(any());
        verify(fileStorageService).save(any());
        verify(homeworkRepository).save(any());
    }
}