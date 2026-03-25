package com.binah.ace.student.interfaces.graphql.input;

import com.binah.ace.student.domain.enums.AssessmentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * GraphQL input for posting a grade.
 *
 * @author Marcos Gustavo
 */
public record PostGradeInput(
        UUID studentId,
        UUID classroomSubjectId,
        AssessmentType assessmentType,
        BigDecimal score,
        BigDecimal weight,
        String description,
        LocalDate assessmentDate,
        Integer year,
        Integer semester,
        Integer bimester
) {
    /**
     * Validates the input.
     *
     * @throws IllegalArgumentException if the data is invalid
     */
    public void validate() {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID is required");
        }

        if (classroomSubjectId == null) {
            throw new IllegalArgumentException("Classroom subject ID is required");
        }

        if (assessmentType == null) {
            throw new IllegalArgumentException("Assessment type is required");
        }

        if (score == null) {
            throw new IllegalArgumentException("Score is required");
        }

        if (score.compareTo(BigDecimal.ZERO) < 0 ||
                score.compareTo(BigDecimal.TEN) > 0) {
            throw new IllegalArgumentException("Score must be between 0 and 10");
        }

        if (assessmentDate == null) {
            throw new IllegalArgumentException("Assessment date is required");
        }

        if (assessmentDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Assessment date cannot be in the future");
        }

        if (year == null || year < 2000 || year > 2100) {
            throw new IllegalArgumentException("Invalid year");
        }

        if (semester == null || semester < 1 || semester > 2) {
            throw new IllegalArgumentException("Semester must be 1 or 2");
        }

        if (bimester != null && (bimester < 1 || bimester > 4)) {
            throw new IllegalArgumentException("Bimester must be between 1 and 4");
        }
    }
}