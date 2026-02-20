package com.binah.ace.auth.interfaces.graphql.dto;

import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.auth.domain.enums.Role;

import java.util.UUID;

/**
 * Login response DTO.
 *
 * Contains the JWT token and basic information about the authenticated user.
 *
 * @author Marcos Gustavo
 */
public record AuthResponseDTO(
        String token,
        UUID userId,
        String username,
        String email,
        Role role,
        UUID entityId
) {
    /**
     * Creates an AuthResponseDTO from a User and a token.
     *
     * @param user Authenticated user
     * @param token Generated JWT
     * @return Response DTO
     */
    public static AuthResponseDTO from(User user, String token) {
        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail().value(),
                user.getRole(),
                user.getEntityId()
        );
    }
}