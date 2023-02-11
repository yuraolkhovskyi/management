package com.sombra.management.service;

import com.sombra.management.dto.UserDTO;
import com.sombra.management.dto.UserNewRoleDTO;
import com.sombra.management.entity.UserEntity;

import java.util.Set;

public interface UserService {

    UserEntity findUserEntityByUserId(final Long userId);

    Set<UserEntity> findUserEntitiesByUserIds(final Set<Long> userIds);

    UserNewRoleDTO assignNewRoleForUser(final UserNewRoleDTO userNewRoleDTO);

    Set<UserDTO> findUserDtosByUserIds(final Set<Long> userIds);
}
