package com.sombra.management.security.user;


import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUsername(final String jwt);

    boolean isTokenValid(final String token, final UserDetails userDetails);

    String generateToken(final UserDetails userDetails);
}
