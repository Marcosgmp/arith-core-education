package com.binah.ace.auth.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration.
 *
 * Configures:
 * - JWT authentication filter
 * - Password encoder
 * - Public vs protected routes
 * - CORS
 * - CSRF (disabled for stateless APIs)
 *
 * @author Marcos Gustavo
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (API stateless com JWT)
                .csrf(csrf -> csrf.disable())

                // Configura autorização
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas (GraphQL schema, login, health check)
                        .requestMatchers("/graphql").permitAll()
                        .requestMatchers("/graphiql").permitAll()
                        .requestMatchers("/actuator/health").permitAll()

                        // Todas as outras rotas requerem autenticação
                        .anyRequest().authenticated()
                )

                // Stateless (sem sessão)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Adiciona filtro JWT antes do filtro padrão
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    /**
     * Password encryption bean.
     *
     * Uses BCrypt with default strength (10 rounds).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}