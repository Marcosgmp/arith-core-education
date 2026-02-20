package com.binah.ace.auth.application.usecase;

import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.auth.domain.repository.UserRepository;
import com.binah.ace.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case: Validate JWT token.
 *
 * Verifies whether the user identified by the token:
 * - Still exists in the system
 * - Is still active
 * - Is not blocked
 *
 * This use case does NOT validate the JWT signature —
 * that responsibility belongs to the JwtTokenProvider.
 *
 * @author Marcos Gustavo
 */
@Service
public class ValidateTokenUseCase {

    private final UserRepository userRepository;

    public ValidateTokenUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates whether the user identified by the token
     * is still allowed to access the system.
     *
     * @param userId User ID extracted from the token
     * @return Valid user
     * @throws EntityNotFoundException if the user does not exist
     * @throws IllegalStateException if the user is blocked or inactive
     */
    public User execute(UUID userId) {
        // Busca usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));

        // Verifica se pode fazer login (ativo e não bloqueado)
        if (!user.canLogin()) {
            throw new IllegalStateException("User cannot access the system");
        }

        return user;
    }
}