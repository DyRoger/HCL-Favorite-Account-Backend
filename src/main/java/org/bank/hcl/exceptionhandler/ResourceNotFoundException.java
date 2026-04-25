package org.bank.hcl.exceptionhandler;

/**
 * Thrown when a requested resource (bank, account, user, etc.) is not found.
 * Mapped to HTTP 404 by GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

