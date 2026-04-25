package org.bank.hcl.controller;

import org.bank.hcl.model.LoginData;
import org.bank.hcl.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Value("${auth.login-hash:}")
    private String configuredHash;

    public Controller(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody LoginData loginData) {
        if (loginData.getCustomerId() == null || loginData.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("customerId is required");
        }
        if (loginData.getHash() == null || loginData.getHash().isBlank()) {
            throw new IllegalArgumentException("hash is required");
        }

        // Step 1: Verify customer exists in DB
        userRepository.findByCustomerId(loginData.getCustomerId())
                .orElseThrow(() -> new BadCredentialsException("Invalid customerId or hash"));

        // Step 2: Verify hash
        if (configuredHash.isBlank() || !safeEquals(configuredHash, loginData.getHash())) {
            throw new BadCredentialsException("Invalid customerId or hash");
        }

        // Step 3: Generate JWT using customerId as subject
        String token = jwtService.generateToken(loginData.getCustomerId());
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
