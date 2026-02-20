package com.binah.ace.auth.interfaces.graphql.dto;

import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.auth.domain.enums.Role;
import com.binah.ace.auth.domain.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO representing a user for GraphQL.
 *
 * Exposes only safe data (no password).
 *
 * @author Marcos Gustavo
 */
public record UserDTO(
        UUID id,
        String username,
        String email,
        Role role,
        UserStatus status,
        UUID entityId,
        LocalDateTime lastLogin,
        LocalDateTime createdAt
) {
    /**
     * Converts a User domain entity to a DTO.
     *
     * @param user Domain entity
     * @return GraphQL DTO
     */
    public static UserDTO from(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail().value(),
                user.getRole(),
                user.getStatus(),
                user.getEntityId(),
                user.getLastLogin(),
                user.getCreatedAt()
        );
    }
}