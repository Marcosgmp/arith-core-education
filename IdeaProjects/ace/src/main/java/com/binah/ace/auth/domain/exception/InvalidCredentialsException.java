package com.binah.ace.auth.domain.exception;

import com.binah.ace.shared.exception.BusinessException;

/**
 * Exception thrown when the provided credentials are invalid.
 *
 * Use cases include:
 * - Username does not exist
 * - Incorrect password
 * - Invalid username/password combination
 *
 * IMPORTANT: Do not reveal whether the issue is the username or the password
 * for security reasons.
 *
 * @author Marcos Gustavo
 */
public class InvalidCredentialsException extends BusinessException {

    /**
     * Default constructor with a generic message.
     *
     * A generic message is used for security reasons,
     * as it does not reveal whether the issue is the username or the password.
     */
    public InvalidCredentialsException() {
        super(
                "INVALID_CREDENTIALS",
                "Invalid username or password"
        );
    }

    /**
     * Constructor with a custom message.
     *
     * Use only in cases where revealing details does not pose
     * a security risk.
     *
     * @param message Custom error message
     */
    public InvalidCredentialsException(String message) {
        super("INVALID_CREDENTIALS", message);
    }
}