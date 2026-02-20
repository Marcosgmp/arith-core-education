package com.binah.ace.auth.interfaces.graphql.resolver;

import com.binah.ace.auth.application.usecase.LoginUseCase;
import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.auth.infrastructure.security.JwtTokenProvider;
import com.binah.ace.auth.interfaces.graphql.dto.AuthResponseDTO;
import com.binah.ace.auth.interfaces.graphql.input.LoginInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * GraphQL resolver for authentication mutations.
 *
 * Acts as a Controller:
 * - Receives GraphQL input
 * - Validates input format
 * - Invokes the Use Case
 * - Returns DTOs
 *
 * Contains NO business logic.
 *
 * @author Marcos Gustavo
 */
@Controller
public class AuthMutationResolver {

    private final LoginUseCase loginUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthMutationResolver(
            LoginUseCase loginUseCase,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.loginUseCase = loginUseCase;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Mutation: login
     *
     * GraphQL example:
     * mutation {
     *   login(input: { username: "admin", password: "123456" }) {
     *     token
     *     userId
     *     username
     *     role
     *   }
     * }
     *
     * @param input Login input data
     * @return JWT token and authenticated user data
     */
    @MutationMapping
    public AuthResponseDTO login(@Argument LoginInput input) {
        // Valida input
        input.validate();

        // Executa login
        User user = loginUseCase.execute(input.username(), input.password());

        // Gera token
        String token = jwtTokenProvider.generateToken(user);

        // Retorna resposta
        return AuthResponseDTO.from(user, token);
    }
}