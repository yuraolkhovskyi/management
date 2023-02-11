package com.sombra.management.service.impl;

import com.sombra.management.dto.UserDTO;
import com.sombra.management.dto.UserNewRoleDTO;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.entity.enumeration.UserRole;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository, modelMapper);
    }

    @Test
    void findUserEntityByUserId_test1() {
        //given
        final UserEntity expected = new UserEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        //when
        final var actual = underTest.findUserEntityByUserId(anyLong());

        //then
        verify(userRepository).findById(any());
        assertNotNull(actual);
    }

    @Test
    void findUserEntityByUserId_test2() {
        Assertions.assertThrows(SystemException.class,
                () -> underTest.findUserEntityByUserId(anyLong()));
    }

    @Test
    void findUserEntityByUserIds_test1() {
        //given
        final UserEntity expected = new UserEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        //when
        final Set<UserEntity> actual = underTest.findUserEntitiesByUserIds(Set.of(1L,2L,3L));

        //then
        verify(userRepository, times(3)).findById(any());
        assertFalse(CollectionUtils.isEmpty(actual));
    }

    @Test
    void findUserDtosByUserIds_test1() {
        //given
        final UserEntity expected = new UserEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        //when
        final Set<UserDTO> actual = underTest.findUserDtosByUserIds(Set.of(1L, 2L, 3L));

        //then
        verify(userRepository, times(3)).findById(any());
        verify(modelMapper, times(3)).map(any(), any());
        assertFalse(CollectionUtils.isEmpty(actual));
    }

    @Test
    void assignNewRoleForUser_test1() {
        //given
        final UserNewRoleDTO request = new UserNewRoleDTO(1L, UserRole.STUDENT);
        final UserEntity expected = UserEntity.builder()
                .id(1L)
                .role(UserRole.STUDENT)
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(userRepository.save(any())).thenReturn(expected);

        //when
        final UserNewRoleDTO actual = underTest.assignNewRoleForUser(request);

        //then
        verify(userRepository).save(any());
        assertNotNull(actual);
        assertEquals(request.getRole(), actual.getRole());
        assertEquals(request.getUserId(), actual.getUserId());
    }
}