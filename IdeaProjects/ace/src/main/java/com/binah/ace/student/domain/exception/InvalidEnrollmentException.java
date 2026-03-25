package com.binah.ace.student.domain.exception;

import com.binah.ace.shared.exception.BusinessException;

/**
 * Exception thrown when an error occurs during an enrollment operation.
 *
 * @author Marcos Gustavo
 */
public class InvalidEnrollmentException extends BusinessException {

    public InvalidEnrollmentException(String message) {
        super("INVALID_ENROLLMENT", message);
    }

    /**
     * Student already enrolled.
     */
    public static InvalidEnrollmentException alreadyEnrolled() {
        return new InvalidEnrollmentException("Student is already enrolled");
    }

    /**
     * Enrollment number already exists.
     */
    public static InvalidEnrollmentException duplicateEnrollmentNumber(String enrollmentNumber) {
        return new InvalidEnrollmentException(
                "Enrollment number already exists: " + enrollmentNumber
        );
    }

    /**
     * CPF duplicate.
     */
    public static InvalidEnrollmentException duplicateCPF(String cpf) {
        return new InvalidEnrollmentException(
                "Student with CPF " + cpf + " already exists"
        );
    }

    /**
     * Student is not active.
     */
    public static InvalidEnrollmentException studentNotActive() {
        return new InvalidEnrollmentException(
                "Only active students can be enrolled in classes"
        );
    }
}