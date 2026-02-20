package com.binah.ace.auth.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for UserJpaEntity.
 *
 * Spring automatically generates the implementation.
 *
 * @author Marcos Gustavo
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {

    /**
     * Finds a user by username.
     *
     * @param username Username to search for
     * @return Optional containing the entity or empty if not found
     */
    Optional<UserJpaEntity> findByUsername(String username);

    /**
     * Finds a user by email.
     *
     * @param email User email
     * @return Optional containing the entity or empty if not found
     */
    Optional<UserJpaEntity> findByEmail(String email);

    /**
     * Checks if a username already exists.
     *
     * @param username Username to check
     * @return true if it exists
     */
    boolean existsByUsername(String username);

    /**
     * Checks if an email already exists.
     *
     * @param email Email to check
     * @return true if it exists
     */
    boolean existsByEmail(String email);
}