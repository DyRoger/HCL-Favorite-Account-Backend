package org.bank.hcl.service;

public interface JwtService {

    String generateToken(String subject);

    boolean isTokenValid(String token);

    String extractSubject(String token);
}

