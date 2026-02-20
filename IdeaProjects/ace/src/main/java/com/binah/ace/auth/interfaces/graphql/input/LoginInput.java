package com.binah.ace.auth.interfaces.graphql.input;

/**
 * Input for the GraphQL login mutation.
 *
 * @author Marcos Gustavo
 */
public record LoginInput(
        String username,
        String password
) {
    /**
     * Validates the input.
     *
     * @throws IllegalArgumentException if the data is invalid
     */
    public void validate() {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
}