package com.sombra.management.security.service.impl;

import com.sombra.management.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.DAYS;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    @Value(value = "${jwt.secret-key}")
    private String SECRET_KEY;
    @Value(value = "${jwt.days-to-expire}")
    private int NUMBER_OF_DAYS_FOR_TOKEN_TO_EXPIRE;

    @Override
    public String extractUsername(final String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(final String jwtToken,
                              final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(final UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(final String jwtToken,
                                final UserDetails userDetails) {
        final String email = extractUsername(jwtToken);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(final String jwtToken) {
        final Date expirationDate = extractExpiration(jwtToken);
        final Date currentDate = new Date();
        return expirationDate.before(currentDate) || expirationDate.equals(currentDate);
    }

    private Date extractExpiration(final String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public String generateToken(final Map<String, Object> extraClaims,
                                final UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + DAYS.toMillis(NUMBER_OF_DAYS_FOR_TOKEN_TO_EXPIRE))) // will expire in 7 days
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(final String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignKey() {
        log.info("Secret key is: {}", SECRET_KEY);
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
