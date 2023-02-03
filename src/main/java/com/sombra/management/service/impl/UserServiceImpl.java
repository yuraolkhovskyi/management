package com.sombra.management.service.impl;

import com.sombra.management.dto.UserNewRoleDTO;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.UserRepository;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserEntity findUserEntityByUserId(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new RuntimeException(String.format("User with id {%d} doesn't exist", userId));
                });
    }

    @Override
    public Set<UserEntity> findUserEntityByUserIds(final Set<Long> userIds) {
        final Set<UserEntity> result = new HashSet<>();
        for (Long userId : userIds) {
            UserEntity userEntity = findUserEntityByUserId(userId);
            result.add(userEntity);
        }
        return result;
    }

    @Override
    public UserNewRoleDTO assignNewRoleForUser(final UserNewRoleDTO userNewRoleDTO) {
        final UserEntity userEntity = findUserEntityByUserId(userNewRoleDTO.getUserId());
        userEntity.setRole(userNewRoleDTO.getRole());
        userRepository.save(userEntity);
        return userNewRoleDTO;
    }
}
