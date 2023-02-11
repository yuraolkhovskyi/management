package com.sombra.management.service.impl;

import com.sombra.management.dto.UserDTO;
import com.sombra.management.dto.UserNewRoleDTO;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.UserRepository;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.sombra.management.exception.ErrorMessageConstants.BAD_REQUEST_ERROR_MESSAGE;
import static com.sombra.management.exception.code.ServiceErrorCode.BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserEntity findUserEntityByUserId(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id {} doesn't exist", userId);
                    throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
                });
    }

    @Override
    public Set<UserEntity> findUserEntitiesByUserIds(final Set<Long> userIds) {
        return userIds.stream()
                .map(this::findUserEntityByUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDTO> findUserDtosByUserIds(final Set<Long> userIds) {
        return userIds.stream()
                .map(this::findUserEntityByUserId)
                .map(e -> modelMapper.map(e, UserDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public UserNewRoleDTO assignNewRoleForUser(final UserNewRoleDTO userNewRoleDTO) {
        final UserEntity userEntity = findUserEntityByUserId(userNewRoleDTO.getUserId());
        userEntity.setRole(userNewRoleDTO.getRole());
        userRepository.save(userEntity);
        return userNewRoleDTO;
    }
}
