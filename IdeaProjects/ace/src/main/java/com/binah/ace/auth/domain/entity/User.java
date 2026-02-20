package com.binah.ace.auth.domain.entity;

import com.binah.ace.auth.domain.enums.Role;
import com.binah.ace.auth.domain.enums.UserStatus;
import com.binah.ace.shared.valueobject.Email;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity that represents a user in the system.
 *
 * This entity is free of persistence concerns and contains
 * no JPA annotations, representing a pure domain model.
 *
 * Responsibilities:
 * - Credential management
 * - Access status control
 * - Login attempt tracking
 * - Blocking rule enforcement
 *
 * @author Marcos Gustavo
 */
@Getter
public class User {

    private final UUID id;
    private String username;
    private Email email;
    private String passwordHash;
    private Role role;
    private UserStatus status;

    // Controle de acesso
    private int failedLoginAttempts;
    private LocalDateTime lockedUntil;
    private LocalDateTime lastLogin;

    // Vínculo com entidades do domínio (Student, Teacher, etc)
    private UUID entityId;

    // Auditoria
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor to create a new user.
     *
     * @param id Unique identifier
     * @param username Username (login)
     * @param email User email address
     * @param passwordHash Password hash (never store plain-text passwords!)
     * @param role User role in the system
     */
    public User(UUID id, String username, Email email, String passwordHash, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = UserStatus.PENDING_ACTIVATION;
        this.failedLoginAttempts = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Full constructor used for reconstruction from the database.
     */
    public User(
            UUID id,
            String username,
            Email email,
            String passwordHash,
            Role role,
            UserStatus status,
            int failedLoginAttempts,
            LocalDateTime lockedUntil,
            LocalDateTime lastLogin,
            UUID entityId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.failedLoginAttempts = failedLoginAttempts;
        this.lockedUntil = lockedUntil;
        this.lastLogin = lastLogin;
        this.entityId = entityId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Activates the user account.
     *
     * @throws IllegalStateException if the user is not pending activation
     */
    public void activate() {
        if (this.status != UserStatus.PENDING_ACTIVATION) {
            throw new IllegalStateException("User is not pending activation");
        }
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Records a failed login attempt.
     *
     * If the number of failed attempts reaches 5,
     * the account is blocked for 30 minutes.
     */
    public void recordFailedLogin() {
        this.failedLoginAttempts++;
        this.updatedAt = LocalDateTime.now();

        // Bloqueia após 5 tentativas
        if (this.failedLoginAttempts >= 5) {
            lockAccount(30); // 30 minutos
        }
    }

    /**
     * Records a successful login.
     *
     * Resets the failed login attempt counter
     * and updates the last login timestamp.
     */
    public void recordSuccessfulLogin() {
        this.failedLoginAttempts = 0;
        this.lastLogin = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Blocks the account for a specified period of time.
     *
     * @param minutes Duration of the block in minutes
     */
    public void lockAccount(int minutes) {
        this.status = UserStatus.BLOCKED;
        this.lockedUntil = LocalDateTime.now().plusMinutes(minutes);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Permanently blocks the account.
     *
     * The account remains blocked until manual intervention.
     */
    public void blockPermanently() {
        this.status = UserStatus.BLOCKED;
        this.lockedUntil = null; // null = permanente
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Unblocks the account.
     */
    public void unblock() {
        this.status = UserStatus.ACTIVE;
        this.lockedUntil = null;
        this.failedLoginAttempts = 0;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Checks whether the user is blocked.
     *
     * Considers both temporary (with expiration) and permanent blocks.
     *
     * @return {@code true} if the user is blocked
     */
    public boolean isLocked() {
        if (this.status != UserStatus.BLOCKED) {
            return false;
        }

        // Se tem data de expiração e já passou, desbloqueia automaticamente
        if (this.lockedUntil != null && LocalDateTime.now().isAfter(this.lockedUntil)) {
            this.status = UserStatus.ACTIVE;
            this.failedLoginAttempts = 0;
            this.lockedUntil = null;
            this.updatedAt = LocalDateTime.now();
            return false;
        }

        return true;
    }

    /**
     * Checks whether the user is allowed to log in.
     *
     * @return {@code true} if the status is {@code ACTIVE} and the user is not blocked
     */
    public boolean canLogin() {
        return this.status == UserStatus.ACTIVE && !isLocked();
    }

    /**
     * Links the user to a domain entity.
     *
     * For example:
     * - If the role is {@code STUDENT}, the {@code entityId} refers to the Student ID
     * - If the role is {@code TEACHER}, the {@code entityId} refers to the Teacher ID
     *
     * @param entityId UUID of the related domain entity
     */
    public void linkToEntity(UUID entityId) {
        this.entityId = entityId;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the user's email address.
     *
     * @param newEmail New email address
     */
    public void changeEmail(Email newEmail) {
        this.email = newEmail;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the password hash.
     *
     * @param newPasswordHash New password hash
     */
    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.updatedAt = LocalDateTime.now();
    }
}