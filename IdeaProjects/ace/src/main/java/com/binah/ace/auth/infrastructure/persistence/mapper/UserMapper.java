package com.binah.ace.auth.infrastructure.persistence.mapper;

import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.auth.infrastructure.persistence.jpa.UserJpaEntity;
import com.binah.ace.shared.valueobject.Email;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User (domain) and UserJpaEntity (infrastructure).
 *
 * Keeps layers separated and decoupled.
 *
 * @author Marcos Gustavo
 */
@Component
public class UserMapper {

    /**
     * Converts Domain Entity → JPA Entity.
     *
     * @param entity Domain layer
     * @return JPA entity
     */

    public User toDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new User(
                entity.getId(),
                entity.getUsername(),
                new Email(entity.getEmail()),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.getStatus(),
                entity.getFailedLoginAttempts(),
                entity.getLockedUntil(),
                entity.getLastLogin(),
                entity.getEntityId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts JPA Entity → Domain Entity.
     *
     * @param domain JPA entity
     * @return Domain entity
     */
    public UserJpaEntity toJpa(User domain) {
        if (domain == null) {
            return null;
        }

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail().value());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setRole(domain.getRole());
        entity.setStatus(domain.getStatus());
        entity.setFailedLoginAttempts(domain.getFailedLoginAttempts());
        entity.setLockedUntil(domain.getLockedUntil());
        entity.setLastLogin(domain.getLastLogin());
        entity.setEntityId(domain.getEntityId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }
}