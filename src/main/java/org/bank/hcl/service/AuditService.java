package org.bank.hcl.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuditService {

    /**
     * Log a successful action.
     *
     * @param customerId customer performing the action
     * @param action     e.g. LOGIN, LOGOUT, ADD_FAVORITE
     * @param resource   e.g. FAVORITE_ACCOUNT (nullable)
     * @param resourceId DB id of affected resource (nullable)
     * @param message    optional info message
     * @param request    HTTP request (used to capture IP address)
     */
    void logSuccess(String customerId, String action, String resource,
                    Long resourceId, String message, HttpServletRequest request);

    /**
     * Log a failed action.
     */
    void logFailure(String customerId, String action, String resource,
                    Long resourceId, String message, HttpServletRequest request);
}

