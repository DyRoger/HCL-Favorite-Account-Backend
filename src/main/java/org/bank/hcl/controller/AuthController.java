package org.bank.hcl.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.bank.hcl.model.LoginData;
import org.bank.hcl.repository.UserRepository;
import org.bank.hcl.service.AuditService;
import org.bank.hcl.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuditService auditService;

    @Value("${auth.login-hash:}")
    private String configuredHash;

    public AuthController(JwtService jwtService, UserRepository userRepository, AuditService auditService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody LoginData loginData,
                                                        HttpServletRequest request) {
        if (loginData.getCustomerId() == null || loginData.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("customerId is required");
        }
        if (loginData.getHash() == null || loginData.getHash().isBlank()) {
            throw new IllegalArgumentException("hash is required");
        }

        // Step 1: Verify customer exists in DB
        userRepository.findByCustomerId(loginData.getCustomerId())
                .orElseThrow(() -> {
                    auditService.logFailure(loginData.getCustomerId(), "LOGIN", null, null,
                            "Login failed: customerId not found", request);
                    return new BadCredentialsException("Invalid customerId or hash");
                });

        // Step 2: Verify hash
        if (configuredHash.isBlank() || !safeEquals(configuredHash, loginData.getHash())) {
            auditService.logFailure(loginData.getCustomerId(), "LOGIN", null, null,
                    "Login failed: invalid hash", request);
            throw new BadCredentialsException("Invalid customerId or hash");
        }

        // Step 3: Generate JWT and audit success
        String token = jwtService.generateToken(loginData.getCustomerId());
        auditService.logSuccess(loginData.getCustomerId(), "LOGIN", null, null,
                "Login successful", request);

        return ResponseEntity.ok(Map.of("token", token, "tokenType", "Bearer"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        // Extract customerId from the validated JWT (set by JwtAuthenticationFilter)
        String customerId = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        auditService.logSuccess(customerId, "LOGOUT", null, null,
                "User logged out", request);

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
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
