package com.binah.ace.student.domain.valueobject;

/**
 * Value Object representing an academic period.
 *
 * Combines year + semester + term segment (optional).
 *
 * Immutable.
 *
 * @author Marcos Gustavo
 */
public record AcademicPeriod(
        int year,
        int semester,
        Integer bimester  // Opcional (pode ser null)
) {

    /**
     * Compact constructor with validation.
     */
    public AcademicPeriod {
        if (year < 2000 || year > 2100) {
            throw new IllegalArgumentException(
                    "Year must be between 2000 and 2100, got: " + year
            );
        }

        if (semester < 1 || semester > 2) {
            throw new IllegalArgumentException(
                    "Semester must be 1 or 2, got: " + semester
            );
        }

        if (bimester != null && (bimester < 1 || bimester > 4)) {
            throw new IllegalArgumentException(
                    "Bimester must be between 1 and 4, got: " + bimester
            );
        }
    }

    /**
     * Creates an academic period without specifying a bimester.
     */
    public static AcademicPeriod of(int year, int semester) {
        return new AcademicPeriod(year, semester, null);
    }

    /**
     * Creates an academic period specifying a bimester.
     */
    public static AcademicPeriod of(int year, int semester, int bimester) {
        return new AcademicPeriod(year, semester, bimester);
    }

    /**
     * Indicates whether a bimester is defined.
     */
    public boolean hasBimester() {
        return bimester != null;
    }

    /**
     * Checks whether this period is before another.
     */
    public boolean isBefore(AcademicPeriod other) {
        if (this.year != other.year) {
            return this.year < other.year;
        }
        if (this.semester != other.semester) {
            return this.semester < other.semester;
        }
        if (this.bimester != null && other.bimester != null) {
            return this.bimester < other.bimester;
        }
        return false;
    }

    @Override
    public String toString() {
        if (bimester != null) {
            return String.format("%d/%d - Bimester %d", year, semester, bimester);
        }
        return String.format("%d/%d", year, semester);
    }
}