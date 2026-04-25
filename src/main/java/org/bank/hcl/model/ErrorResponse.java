package org.bank.hcl.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Standard error response model for API error handling.
 * Provides consistent error information structure across all endpoints.
 * Includes support for validation errors and distributed tracing.
 *
 * @author Kaustabh Adak
 * @since 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Timestamp when the error occurred.
     * Automatically set to current time when object is created.
     */
    private String timestamp;

    /**
     * HTTP status code of the error.
     * Standard HTTP response codes (400, 401, 403, 404, 500, etc.).
     */
    private int status;

    /**
     * Brief error category or type.
     * Examples: "Bad Request", "Unauthorized", "Internal Server Error"
     */
    private String error;

    /**
     * Detailed error message for debugging.
     * Human-readable description of what went wrong.
     */
    private String message;

    /**
     * API endpoint path where the error occurred.
     * Helps identify the source of the error.
     */
    private String path;

    /**
     * Trace identifier for distributed tracing.
     * Used to correlate logs across multiple services.
     */
    private String traceId;

    /**
     * Default constructor that sets timestamp to current time.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now().toString();
    }

    /**
     * Constructor for basic error information.
     *
     * @param status HTTP status code
     * @param error Error category
     * @param message Detailed error message
     * @param path API endpoint path
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
