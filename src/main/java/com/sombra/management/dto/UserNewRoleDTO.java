package com.sombra.management.dto;

import com.sombra.management.entity.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserNewRoleDTO {

    private Long userId;
    private UserRole role;

}
