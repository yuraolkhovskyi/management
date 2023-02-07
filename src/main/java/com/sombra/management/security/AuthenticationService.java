package com.sombra.management.security;

import com.sombra.management.security.dto.AuthenticationRequest;
import com.sombra.management.security.dto.AuthenticationResponse;
import com.sombra.management.security.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(final RegisterRequest request);

    AuthenticationResponse authenticate(final AuthenticationRequest authenticationRequest);
}
