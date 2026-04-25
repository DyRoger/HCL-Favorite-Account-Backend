package org.bank.hcl.controller;

import org.bank.hcl.model.LoginHash;
import org.bank.hcl.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    private final JwtService jwtService;

    @Value("${auth.login-hash:}")
    private String configuredHash;

    public Controller(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody LoginHash loginHash) {
        if (loginHash == null || loginHash.getHash() == null || loginHash.getHash().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "hash is required"));
        }

        if (configuredHash == null || configuredHash.isBlank() || !safeEquals(configuredHash, loginHash.getHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "invalid login hash"));
        }

        String token = jwtService.generateToken("fav-account-client");
        return ResponseEntity.ok(Map.of("token", token, "tokenType", "Bearer"));
    }

    private boolean safeEquals(String left, String right) {
        byte[] leftBytes = left.getBytes(StandardCharsets.UTF_8);
        byte[] rightBytes = right.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(leftBytes, rightBytes);
    }
}
