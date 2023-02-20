package com.sombra.management.controller;

import com.sombra.management.exception.SystemException;
import com.sombra.management.security.dto.AuthenticationRequest;
import com.sombra.management.security.dto.AuthenticationResponse;
import com.sombra.management.security.dto.RegisterRequest;
import com.sombra.management.security.service.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static com.sombra.management.exception.ErrorMessageConstants.BAD_REQUEST_ERROR_MESSAGE;
import static com.sombra.management.exception.code.ServiceErrorCode.BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    private AuthenticationService authenticationService;
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        authenticationService = mock(AuthenticationService.class);
        authenticationController = new AuthenticationController(authenticationService);
    }

    @Test
    void register__test1() {

        final var expected = new AuthenticationResponse("token");
        when(authenticationService.register(any())).thenReturn(expected);

        final var registerRequest = new RegisterRequest("fName", "lName", "email@gmail.com", "pass");
        final ResponseEntity<AuthenticationResponse> actual = authenticationController.register(registerRequest);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected.getToken(), Objects.requireNonNull(actual.getBody()).getToken());
    }

    @Test
    void register__test2() {

        when(authenticationService.register(any())).thenThrow(new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST));

        final var registerRequest = new RegisterRequest("fName", "lName", "email@gmail.com", "pass");

        Assertions.assertThrows(
                SystemException.class,
                () -> authenticationController.register(registerRequest),
                BAD_REQUEST_ERROR_MESSAGE);
    }

    @Test
    void authenticate() {
        final var expected = new AuthenticationResponse("token");
        when(authenticationService.authenticate(any())).thenReturn(expected);

        AuthenticationRequest request = new AuthenticationRequest("email", "pass");
        final ResponseEntity<AuthenticationResponse> actual = authenticationController.authenticate(request);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected.getToken(), Objects.requireNonNull(actual.getBody()).getToken());
    }
}