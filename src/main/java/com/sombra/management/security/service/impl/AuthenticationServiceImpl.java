package com.sombra.management.security.service.impl;

import com.sombra.management.entity.UserEntity;
import com.sombra.management.entity.enumeration.UserRole;
import com.sombra.management.repository.UserRepository;
import com.sombra.management.security.service.AuthenticationService;
import com.sombra.management.security.dto.AuthenticationRequest;
import com.sombra.management.security.dto.AuthenticationResponse;
import com.sombra.management.security.dto.RegisterRequest;
import com.sombra.management.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(final RegisterRequest request) {
        final UserEntity userSecurityEntity = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.STUDENT)
                .build();

        userSecurityRepository.save(userSecurityEntity);
        final String token = jwtService.generateToken(userSecurityEntity);
        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse authenticate(final AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        UserEntity userSecurityEntity = userSecurityRepository
                .findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        final String token = jwtService.generateToken(userSecurityEntity);
        return new AuthenticationResponse(token);
    }
}
