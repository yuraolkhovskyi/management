package com.sombra.management.controller;

import com.sombra.management.dto.UserNewRoleDTO;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    private final UserService userService;

    //[SECURITY] Admin should be able to assign a role for a new user;
    @PostMapping(value = "/assign/role")
    public ResponseEntity<UserNewRoleDTO> assignNewRoleForUser(@RequestBody final UserNewRoleDTO userNewRoleDTO) {
        return ResponseEntity.ok().body(userService.assignNewRoleForUser(userNewRoleDTO));
    }

}
