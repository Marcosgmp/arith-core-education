package com.binah.ace.student.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value Object representing a GPA (Grade Point Average).
 *
 * GPA is the weighted average of a student's grades.
 * Scale: 0.00 to 10.00
 *
 * Immutable.
 *
 * @author Marcos Gustavo
 */
public record GPA(BigDecimal value) {

    private static final BigDecimal MIN_GPA = BigDecimal.ZERO;
    private static final BigDecimal MAX_GPA = BigDecimal.TEN;

    /**
     * Compact constructor with validation.
     */
    public GPA {
        if (value == null) {
            throw new IllegalArgumentException("GPA cannot be null");
        }

        if (value.compareTo(MIN_GPA) < 0 || value.compareTo(MAX_GPA) > 0) {
            throw new IllegalArgumentException(
                    "GPA must be between 0.00 and 10.00, got: " + value
            );
        }

        // Arredonda para 2 casas decimais
        value = value.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Creates a GPA instance from a double value.
     */
    public static GPA of(double value) {
        return new GPA(BigDecimal.valueOf(value));
    }

    /**
     * Indicates whether the student has passed (GPA >= 7.0).
     */
    public boolean isApproved() {
        return value.compareTo(BigDecimal.valueOf(7.0)) >= 0;
    }

    /**
     * Indicates whether the student has failed (GPA >= 7.0).
     */
    public boolean isFailed() {
        return value.compareTo(BigDecimal.valueOf(5.0)) < 0;
    }

    /**
     * Checks if the student is in remediation (5.0 <= GPA < 7.0).
     */
    public boolean needsRecovery() {
        return value.compareTo(BigDecimal.valueOf(5.0)) >= 0
                && value.compareTo(BigDecimal.valueOf(7.0)) < 0;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}