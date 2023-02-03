package com.sombra.management.service;

import com.sombra.management.dto.UserNewRoleDTO;
import com.sombra.management.entity.UserEntity;

import java.util.Set;

public interface UserService {

    UserEntity findUserEntityByUserId(final Long userId);

    Set<UserEntity> findUserEntityByUserIds(final Set<Long> userIds);

    UserNewRoleDTO assignNewRoleForUser(UserNewRoleDTO userNewRoleDTO);
}
