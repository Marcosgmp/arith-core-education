package com.binah.ace.auth.domain.repository;

import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.shared.valueobject.Email;

import java.util.Optional;
import java.util.UUID;

/**
 * Persistence contract for the User entity.
 *
 * This is a pure domain interface with NO dependency on JPA
 * or any other framework.
 *
 * The implementation is provided in the Infrastructure layer.
 *
 * @author Marcos Gustavo
 */
public interface UserRepository {

    /**
     * Finds a user by its unique identifier.
     *
     * @param id Unique identifier
     * @return An {@link Optional} containing the user if found, or empty otherwise
     */
    Optional<User> findById(UUID id);

    /**
     * Finds a user by username.
     *
     * Used during the login process.
     *
     * @param username Username
     * @return An {@link Optional} containing the user if found, or empty otherwise
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by email address.
     *
     * @param email User email address
     * @return An {@link Optional} containing the user if found, or empty otherwise
     */
    Optional<User> findByEmail(Email email);

    /**
     * Checks whether a user with the given username already exists.
     *
     * @param username Username to check
     * @return {@code true} if the username already exists
     */
    boolean existsByUsername(String username);

    /**
     * Checks whether a user with the given email already exists.
     *
     * @param email Email address to check
     * @return {@code true} if the email already exists
     */
    boolean existsByEmail(Email email);

    /**
     * Saves a user (create or update).
     *
     * @param user User to be saved
     * @return Saved user
     */
    User save(User user);

    /**
     * Deletes a user.
     *
     * @param id Identifier of the user to delete
     */
    void deleteById(UUID id);
}