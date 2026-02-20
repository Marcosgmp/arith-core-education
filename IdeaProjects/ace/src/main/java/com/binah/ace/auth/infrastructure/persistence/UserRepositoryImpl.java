package com.binah.ace.auth.infrastructure.persistence;

import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.auth.domain.repository.UserRepository;
import com.binah.ace.auth.infrastructure.persistence.jpa.UserJpaEntity;
import com.binah.ace.auth.infrastructure.persistence.jpa.UserJpaRepository;
import com.binah.ace.auth.infrastructure.persistence.mapper.UserMapper;
import com.binah.ace.shared.valueobject.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA implementation of UserRepository.
 *
 * Translates domain-level calls to JPA operations and vice versa.
 *
 * @author Marcos Gustavo
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    public UserRepositoryImpl(
            UserJpaRepository jpaRepository,
            UserMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.value())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.existsByEmail(email.value());
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toJpa(user);
        UserJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}