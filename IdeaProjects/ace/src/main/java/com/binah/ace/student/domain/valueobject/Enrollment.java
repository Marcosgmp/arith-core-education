package com.binah.ace.student.domain.valueobject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Value Object representing a Student Enrollment.
 *
 * Contains the student's enrollment number and enrollment date.
 *
 * The enrollment number is unique and generated in the format:
 * YYYYMMDD-XXX (year+month+day + sequential number)
 *
 * Example: 20260215-001
 *
 * Immutable.
 *
 * @author Marcos Gustavo
 */
public record Enrollment(
        String enrollmentNumber,
        LocalDate enrollmentDate
) {

    private static final String ENROLLMENT_PATTERN = "\\d{8}-\\d{3}";

    /**
     * Compact constructor with validation.
     */
    public Enrollment {
        if (enrollmentNumber == null || enrollmentNumber.isBlank()) {
            throw new IllegalArgumentException("Enrollment number cannot be null or empty");
        }

        if (!enrollmentNumber.matches(ENROLLMENT_PATTERN)) {
            throw new IllegalArgumentException(
                    "Invalid enrollment number format. Expected YYYYMMDD-XXX, got: " + enrollmentNumber
            );
        }

        if (enrollmentDate == null) {
            throw new IllegalArgumentException("Enrollment date cannot be null");
        }

        if (enrollmentDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Enrollment date cannot be in the future: " + enrollmentDate
            );
        }
    }

    /**
     * Generates a new enrollment number.
     *
     * @param sequenceNumber Daily sequential number (001, 002, etc)
     * @return Enrollment containing the generated number
     */
    public static Enrollment generate(int sequenceNumber) {
        LocalDate today = LocalDate.now();

        String datePrefix = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sequence = String.format("%03d", sequenceNumber);
        String enrollmentNumber = datePrefix + "-" + sequence;

        return new Enrollment(enrollmentNumber, today);
    }

    /**
     * Creates an Enrollment from an existing enrollment number.
     */
    public static Enrollment of(String enrollmentNumber, LocalDate enrollmentDate) {
        return new Enrollment(enrollmentNumber, enrollmentDate);
    }

    /**
     * Extracts the year from the enrollment number.
     */
    public int getYear() {
        return Integer.parseInt(enrollmentNumber.substring(0, 4));
    }

    /**
     * Extracts the month from the enrollment number.
     */
    public int getMonth() {
        return Integer.parseInt(enrollmentNumber.substring(4, 6));
    }

    /**
     * Extracts the sequential part of the enrollment number.
     */
    public int getSequence() {
        return Integer.parseInt(enrollmentNumber.substring(9, 12));
    }

    /**
     * Formats the enrollment number for display.
     */
    public String getFormatted() {
        return enrollmentNumber;
    }

    @Override
    public String toString() {
        return enrollmentNumber;
    }
}