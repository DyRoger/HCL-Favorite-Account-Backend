package org.bank.hcl.controller;

import org.bank.hcl.model.LoginHash;
import org.bank.hcl.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

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
            throw new IllegalArgumentException("hash is required");
        }

        if (configuredHash == null || configuredHash.isBlank() || !safeEquals(configuredHash, loginHash.getHash())) {
            throw new BadCredentialsException("Invalid login hash");
        }

        String token = jwtService.generateToken("fav-account-client");
        return ResponseEntity.ok(Map.of("token", token, "tokenType", "Bearer"));
    }

    @GetMapping("/test-auth")
    public String test() {
        return "Tested";
    }

    private boolean safeEquals(String left, String right) {
        byte[] leftBytes = left.getBytes(StandardCharsets.UTF_8);
        byte[] rightBytes = right.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(leftBytes, rightBytes);
    }
}
