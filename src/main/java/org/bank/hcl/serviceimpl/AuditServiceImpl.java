package org.bank.hcl.serviceimpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.bank.hcl.model.AuditLog;
import org.bank.hcl.repository.AuditLogRepository;
import org.bank.hcl.service.AuditService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void logSuccess(String customerId, String action, String resource,
                           Long resourceId, String message, HttpServletRequest request) {
        save(customerId, action, resource, resourceId, "SUCCESS", message, request);
    }

    @Override
    public void logFailure(String customerId, String action, String resource,
                           Long resourceId, String message, HttpServletRequest request) {
        save(customerId, action, resource, resourceId, "FAIL", message, request);
    }

    private void save(String customerId, String action, String resource,
                      Long resourceId, String status, String message,
                      HttpServletRequest request) {
        AuditLog log = AuditLog.builder()
                .customerId(customerId)
                .action(action)
                .resource(resource)
                .resourceId(resourceId)
                .status(status)
                .message(message)
                .ipAddress(getClientIp(request))
                .createdAt(LocalDateTime.now())
                .build();
        auditLogRepository.save(log);
    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}

