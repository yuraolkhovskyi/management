package com.sombra.management.service.impl;

import com.sombra.management.dto.LessonDTO;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private ModelMapper modelMapper;

    private LessonServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new LessonServiceImpl(lessonRepository, modelMapper);
    }

    @Test
    void getLessonsByCourseId_test1() {
        //given
        final var lessonEntity = new LessonEntity(1L, "test", null, null, null);
        final var lessonDTO = new LessonDTO(1L, "test");
        when(lessonRepository.getLessonEntitiesByCourse_Id(anyLong())).thenReturn(Set.of(lessonEntity));
        when(modelMapper.map(any(), any())).thenReturn(lessonDTO);

        //when
        final Set<LessonDTO> actual = underTest.getLessonsByCourseId(anyLong());
        final LessonDTO actualLesson = actual.stream().findFirst().get();

        //then
        verify(lessonRepository).getLessonEntitiesByCourse_Id(any());
        verify(modelMapper).map(any(), any());
        assertEquals(lessonDTO.getId(), actualLesson.getId());
        assertEquals(lessonDTO.getTitle(), actualLesson.getTitle());
    }

    @Test
    void findLessonEntityById_test1() {
        //given
        final var lessonEntity = new LessonEntity(1L, "test", null, null, null);
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lessonEntity));

        //when
        final LessonEntity actual = underTest.findLessonEntityById(anyLong());

        //then
        verify(lessonRepository).findById(any());
        assertEquals(lessonEntity.getId(), actual.getId());
        assertEquals(lessonEntity.getTitle(), actual.getTitle());

    }

    @Test
    void findLessonEntityById_test2() {
        Assertions.assertThrows(SystemException.class,
                () -> underTest.findLessonEntityById(anyLong()));
    }
}