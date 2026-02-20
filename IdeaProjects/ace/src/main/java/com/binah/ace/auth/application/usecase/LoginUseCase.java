package com.binah.ace.auth.application.usecase;

import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.auth.domain.exception.BlockedUserException;
import com.binah.ace.auth.domain.exception.InvalidCredentialsException;
import com.binah.ace.auth.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case: User login.
 *
 * Orchestrates the authentication flow:
 * 1. Retrieves the user by username
 * 2. Validates the password
 * 3. Checks whether the user is blocked
 * 4. Registers a successful or failed login attempt
 * 5. Returns the authenticated user data
 *
 * This use case does NOT generate a JWT —
 * that responsibility belongs to the Infrastructure layer.
 *
 * @author Marcos Gustavo
 */
@Service
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Executes the login process.
     *
     * @param username Username
     * @param rawPassword Plain-text password (will be compared against the hash)
     * @return Authenticated user
     * @throws InvalidCredentialsException if the provided credentials are invalid
     * @throws BlockedUserException if the user is blocked
     */
    @Transactional
    public User execute(String username, String rawPassword) {
        // 1. Busca usuário por username
        User user = userRepository.findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        // 2. Verifica se está bloqueado
        if (user.isLocked()) {
            if (user.getLockedUntil() != null) {
                throw new BlockedUserException(user.getLockedUntil());
            } else {
                throw new BlockedUserException();
            }
        }

        // 3. Verifica se pode fazer login
        if (!user.canLogin()) {
            throw new InvalidCredentialsException("Account is not active");
        }

        // 4. Valida senha
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            user.recordFailedLogin();
            userRepository.save(user);
            throw new InvalidCredentialsException();
        }

        // 5. Login bem-sucedido - registra
        user.recordSuccessfulLogin();
        userRepository.save(user);

        return user;
    }
}