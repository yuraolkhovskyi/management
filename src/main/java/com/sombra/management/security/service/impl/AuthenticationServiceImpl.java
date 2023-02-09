package com.sombra.management.security.service.impl;

import com.sombra.management.entity.UserEntity;
import com.sombra.management.entity.enumeration.UserRole;
import com.sombra.management.exception.SystemException;
import com.sombra.management.exception.code.ServiceErrorCode;
import com.sombra.management.repository.UserRepository;
import com.sombra.management.security.service.AuthenticationService;
import com.sombra.management.security.dto.AuthenticationRequest;
import com.sombra.management.security.dto.AuthenticationResponse;
import com.sombra.management.security.dto.RegisterRequest;
import com.sombra.management.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.sombra.management.exception.ErrorMessageConstants.BAD_REQUEST_ERROR_MESSAGE;
import static com.sombra.management.exception.code.ServiceErrorCode.BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(final RegisterRequest request) {
        final String userEmail = request.getEmail();
        if (userSecurityRepository.findByEmail(userEmail).isPresent()) {

            log.error("Not able to register user. User with the specified email {} is already registered", userEmail);
            throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
        }

        final UserEntity userSecurityEntity = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(userEmail)
                .registrationDate(LocalDateTime.now())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.STUDENT)
                .build();

        userSecurityRepository.save(userSecurityEntity);
        final String token = jwtService.generateToken(userSecurityEntity);
        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse authenticate(final AuthenticationRequest authenticationRequest) {
        final String userEmail = authenticationRequest.getEmail();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userEmail,
                        authenticationRequest.getPassword()
                )
        );
        final UserEntity userEntity = userSecurityRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> {
                    log.error("Not able to authenticate user. No user found by the specified email {}", userEmail);
                    throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
                });
        final String token = jwtService.generateToken(userEntity);
        return new AuthenticationResponse(token);
    }
}
