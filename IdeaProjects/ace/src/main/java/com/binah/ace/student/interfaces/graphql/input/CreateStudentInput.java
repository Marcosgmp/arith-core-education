package com.binah.ace.student.interfaces.graphql.input;

import java.time.LocalDate;

/**
 * GraphQL input for creating a student.
 *
 * @author Marcos Gustavo
 */
public record CreateStudentInput(
        String fullName,
        String cpf,
        String email,
        LocalDate birthDate,
        String phone,
        String address,
        String guardianName,
        String guardianPhone,
        String guardianEmail
) {
    /**
     * Validates the input.
     *
     * @throws IllegalArgumentException if the data is invalid
     */
    public void validate() {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name is required");
        }

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF is required");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date is required");
        }

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }

        if (guardianName == null || guardianName.isBlank()) {
            throw new IllegalArgumentException("Guardian name is required");
        }

        if (guardianEmail == null || guardianEmail.isBlank()) {
            throw new IllegalArgumentException("Guardian email is required");
        }
    }
}