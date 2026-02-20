package com.binah.ace.auth.infrastructure.security;

import com.binah.ace.auth.application.usecase.ValidateTokenUseCase;
import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.shared.constants.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * JWT authentication filter.
 *
 * Intercepts ALL incoming requests and:
 * 1. Extracts the token from the Authorization header
 * 2. Validates the token
 * 3. Loads the user
 * 4. Sets the Spring Security context
 *
 * @author Marcos Gustavo
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final ValidateTokenUseCase validateTokenUseCase;

    public JwtAuthenticationFilter(
            JwtTokenProvider tokenProvider,
            ValidateTokenUseCase validateTokenUseCase
    ) {
        this.tokenProvider = tokenProvider;
        this.validateTokenUseCase = validateTokenUseCase;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            // 1. Extrai token do header
            String token = extractTokenFromRequest(request);

            // 2. Se não tem token, continua sem autenticar
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 3. Valida assinatura e expiração do token
            if (!tokenProvider.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 4. Extrai userId do token
            UUID userId = tokenProvider.getUserIdFromToken(token);

            // 5. Valida se usuário ainda pode acessar
            User user = validateTokenUseCase.execute(userId);

            // 6. Cria autenticação do Spring Security
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getId(),
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // 7. Seta no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // Log do erro (não bloqueia requisição)
            logger.error("Cannot set user authentication", e);
        }

        // Continua o filtro
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header.
     *
     * Expected format: "Authorization: Bearer <token>"
     *
     * @param request HttpServletRequest
     * @return Token, or {@code null} if not present
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.JWT_HEADER_STRING);

        if (bearerToken != null && bearerToken.startsWith(SecurityConstants.JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(SecurityConstants.JWT_TOKEN_PREFIX.length());
        }

        return null;
    }
}