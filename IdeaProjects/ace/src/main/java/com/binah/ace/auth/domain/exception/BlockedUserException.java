package com.binah.ace.auth.domain.exception;

import com.binah.ace.shared.exception.BusinessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exception thrown when a blocked user attempts to log in.
 *
 * Use cases include:
 * - User blocked due to failed login attempts
 * - User blocked by administrative action
 *
 * @author Marcos Gustavo
 */
public class BlockedUserException extends BusinessException {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Constructor for a temporary block with an expiration date.
     *
     * @param unlockedAt Date and time when the block expires
     */
    public BlockedUserException(LocalDateTime unlockedAt) {
        super(
                "USER_BLOCKED",
                String.format(
                        "Your account is temporarily blocked. Try again after %s",
                        unlockedAt.format(FORMATTER)
                )
        );
    }

    /**
     * Constructor for a permanent block.
     */
    public BlockedUserException() {
        super(
                "USER_BLOCKED",
                "Your account has been blocked. Please contact support."
        );
    }

    /**
     * Constructor with a custom message.
     *
     * @param message Custom message
     */
    public BlockedUserException(String message) {
        super("USER_BLOCKED", message);
    }
}