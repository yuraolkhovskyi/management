package com.sombra.management.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/health")
public class HealthRestController {

    @GetMapping
    public String health() {
        log.info("The application is healthy");
        return "healthy";
    }


}
