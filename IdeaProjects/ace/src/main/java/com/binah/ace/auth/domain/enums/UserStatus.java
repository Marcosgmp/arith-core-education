package com.binah.ace.auth.domain.enums;

/**
 /**
 * User status in the system.
 *
 * Controls whether the user is allowed to access the system.
 *
 * @author Marcos Gustavo
 */
public enum UserStatus {

    /**
     * Active user.
     *
     * The user is allowed to log in.
     */
    ACTIVE("Active"),

    /**
     * Blocked user.
     *
     * The user is not allowed to log in due to failed login attempts
     * or administrative action.
     */
    BLOCKED("Blocked"),

    /**
     * User created but not yet activated.
     *
     * The account is pending activation, for example via email.
     *
     */
    PENDING_ACTIVATION("Pending Activation");

    private final String displayName;

    UserStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks whether the user is allowed to log in.
     *
     * @return {@code true} if the status is {@code ACTIVE}
     */
    public boolean canLogin() {
        return this == ACTIVE;
    }

    /**
     * Checks whether the user is blocked.
     *
     * @return {@code true} if the status is {@code BLOCKED}
     */
    public boolean isBlocked() {
        return this == BLOCKED;
    }
}
