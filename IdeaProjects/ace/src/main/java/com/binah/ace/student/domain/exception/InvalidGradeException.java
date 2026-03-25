package com.binah.ace.student.domain.exception;

import com.binah.ace.shared.exception.BusinessException;

/**
 * Exception thrown when there is an error while posting or updating a grade.
 *
 * @author Marcos Gustavo
 */
public class InvalidGradeException extends BusinessException {

    public InvalidGradeException(String message) {
        super("INVALID_GRADE", message);
    }

    /**
     * Score outside the valid range.
     */
    public static InvalidGradeException scoreOutOfRange(double score) {
        return new InvalidGradeException(
                String.format("Score must be between 0.00 and 10.00, got: %.2f", score)
        );
    }

    /**
     * Invalid weight.
     */
    public static InvalidGradeException invalidWeight(double weight) {
        return new InvalidGradeException(
                String.format("Weight must be greater than 0, got: %.2f", weight)
        );
    }

    /**
     * Student not enrolled in the class/subject.
     */
    public static InvalidGradeException studentNotEnrolled() {
        return new InvalidGradeException(
                "Cannot post grade: student is not enrolled in this class"
        );
    }

    /**
     * Invalid academic period.
     */
    public static InvalidGradeException invalidPeriod(String reason) {
        return new InvalidGradeException(
                "Invalid academic period: " + reason
        );
    }
}