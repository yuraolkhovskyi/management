package com.sombra.management.controller;

import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    private final UserService userService;


}
