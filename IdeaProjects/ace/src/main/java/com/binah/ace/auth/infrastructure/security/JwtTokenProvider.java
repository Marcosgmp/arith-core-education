package com.binah.ace.auth.infrastructure.security;

import com.binah.ace.auth.domain.entity.User;
import com.binah.ace.shared.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * JWT token provider.
 *
 * Responsible for:
 * - Generating JWT tokens
 * - Validating tokens
 * - Extracting information from tokens
 *
 * Uses HMAC-SHA256 for signing.
 *
 * @author Marcos Gustavo
 */
@Component
public class JwtTokenProvider {

    private final Key signingKey;

    public JwtTokenProvider() {
        // Gera chave a partir do secret
        // Em produção, usar chave do application.properties
        this.signingKey = Keys.hmacShaKeyFor(
                SecurityConstants.JWT_SECRET_KEY.getBytes()
        );
    }

    /**
     * Generates a JWT token for a user.
     *
     * Included claims:
     * - sub: userId
     * - username
     * - email
     * - role
     * - entityId (if present)
     *
     * @param user Authenticated user
     * @return JWT token
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION_MS);

        var builder = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail().value())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(signingKey, SignatureAlgorithm.HS256);

        // Adiciona entityId se existir
        if (user.getEntityId() != null) {
            builder.claim("entityId", user.getEntityId().toString());
        }

        return builder.compact();
    }

    /**
     * Extracts the user ID from the JWT token.
     *
     * @param token JWT token
     * @return User UUID
     */
    public UUID getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return UUID.fromString(claims.getSubject());
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token JWT token
     * @return Username
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("username", String.class);
    }

    /**
     * Extracts the role from the JWT token.
     *
     * @param token Token JWT
     * @return Role as string
     */
    public String getRoleFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    /**
     * Validates whether the JWT token is valid.
     *
     * Checks:
     * - Signature
     * - Expiration
     *
     * @param token JWT token
     * @return {@code true} if the token is valid
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the claims from the JWT token.
     *
     * @param token JWT token
     * @return JWT claims
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}