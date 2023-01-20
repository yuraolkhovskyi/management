package com.sombra.management.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestRestController {


    @GetMapping(value = "/health")
    public String health() {
        log.info("healthy");
        return "healthy";
    }

    @GetMapping(value = "/health/v2")
    public String health2() {
        log.info("healthy");
        return "healthy-2";
    }


}
