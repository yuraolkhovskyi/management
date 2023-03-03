package com.sombra.management.service.impl;

import com.sombra.management.dto.CourseDTO;
import com.sombra.management.dto.CourseResDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.dto.UserDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.entity.enumeration.UserRole;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.CourseRepository;
import com.sombra.management.service.LessonService;
import com.sombra.management.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LessonService lessonService;

    private CourseServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new CourseServiceImpl(courseRepository, userService, modelMapper, lessonService);
    }


    @Test
    void getCoursesByUserId_test1() {
        try (final MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            //given
            final Authentication authentication = mock(Authentication.class);
            final SecurityContext securityContext = mock(SecurityContext.class);
            final UserEntity userEntity = new UserEntity();

            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userEntity);

            //when
            Assertions.assertThrows(SystemException.class,
                    () -> underTest.getCoursesByUserId(1L));
        }

    }

    @Test
    void getCoursesByUserId_test2() {
        try (final MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            //given
            final Authentication authentication = mock(Authentication.class);
            final SecurityContext securityContext = mock(SecurityContext.class);
            final UserEntity userEntity = UserEntity.builder()
                    .id(1L)
                    .role(UserRole.ADMIN)
                    .build();

            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userEntity);

            final CourseEntity courseEntity = new CourseEntity();
            final CourseResDTO expected = new CourseResDTO();
            when(courseRepository.getCourseEntitiesByUserId(anyLong())).thenReturn(Set.of(courseEntity));
            when(modelMapper.map(any(), any())).thenReturn(expected);

            //when
            final Set<CourseResDTO> actual = underTest.getCoursesByUserId(1L);

            //then
            mocked.verify(SecurityContextHolder::getContext);
            verify(courseRepository).getCourseEntitiesByUserId(anyLong());
            verify(modelMapper).map(any(), any());
            assertEquals(1, actual.size());
        }

    }

    @Test
    void findById_test1() {
        //given
        final CourseEntity courseEntity = new CourseEntity();
        final CourseDTO expected = new CourseDTO();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(courseEntity));
        when(modelMapper.map(any(), any())).thenReturn(expected);

        //when
        final CourseDTO actual = underTest.findById(anyLong());

        //then
        verify(courseRepository).findById(anyLong());
        verify(modelMapper).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    void findCourseEntityById_test1() {
        //given
        final CourseEntity expected = new CourseEntity();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        //when
        final CourseEntity actual = underTest.findCourseEntityById(anyLong());

        //then
        verify(courseRepository).findById(anyLong());
        assertEquals(expected, actual);

    }

    @Test
    void assignInstructorToCourse_test1() {
        //given
        final UserCourseDTO expected = new UserCourseDTO(1L, 1L);
        final CourseEntity courseEntity = new CourseEntity();
        final UserEntity userEntity = new UserEntity();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(courseEntity));
        when(courseRepository.save(any())).thenReturn(courseEntity);
        when(userService.findUserEntitiesByUserIds(anySet())).thenReturn(Set.of(userEntity));

        //when
        final UserCourseDTO actual = underTest.assignInstructorToCourse(expected);

        //then
        verify(courseRepository).findById(anyLong());
        verify(courseRepository).save(any());
        verify(userService).findUserEntitiesByUserIds(anySet());
        assertEquals(expected, actual);
    }

    @Test
    void getStudentsByCourseId_test1() {
        //given
        final CourseEntity courseEntity = new CourseEntity();

        final UserDTO student1 = new UserDTO(1L, "test", "test", "test", UserRole.STUDENT);
        final UserDTO student2 = new UserDTO(2L, "test", "test", "test", UserRole.STUDENT);

        final CourseDTO courseDTO = new CourseDTO("test", LocalDate.now(), LocalDate.now(), Set.of(1L, 2L, 3L), Set.of(),
                Set.of(student1, student2));

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(courseEntity));
        when(modelMapper.map(any(), any())).thenReturn(courseDTO);
        when(userService.findUserDtosByUserIds(anySet())).thenReturn(Set.of(student1, student2));

        //when
        final Set<UserDTO> actual = underTest.getStudentsByCourseId(anyLong());

        //then
        verify(courseRepository).findById(anyLong());
        verify(modelMapper).map(any(), any());
        verify(userService).findUserDtosByUserIds(Set.of(1L, 2L));
        assertFalse(CollectionUtils.isEmpty(actual));
        actual.forEach(e -> assertSame(e.getRole(), UserRole.STUDENT));

    }
}