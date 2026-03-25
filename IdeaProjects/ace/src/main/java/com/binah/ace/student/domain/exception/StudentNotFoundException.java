package com.binah.ace.student.domain.exception;

import com.binah.ace.shared.exception.EntityNotFoundException;

import java.util.UUID;

/**
 * Exception thrown when a student is not found.
 *
 * @author Marcos Gustavo
 */
public class StudentNotFoundException extends EntityNotFoundException {

    public StudentNotFoundException(UUID id) {
        super("Student", id);
    }

    public StudentNotFoundException(String cpf) {
        super("Student with CPF " + cpf + " not found", true);
    }

    public StudentNotFoundException(String field, String value) {
        super(String.format("Student with %s '%s' not found", field, value), true);
    }
}