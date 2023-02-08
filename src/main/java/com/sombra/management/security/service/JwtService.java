package com.sombra.management.security.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUsername(final String jwtToken);

    boolean isTokenValid(final String jwtToken, final UserDetails userDetails);

    String generateToken(final UserDetails userDetails);
}
