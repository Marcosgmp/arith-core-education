package com.binah.ace.student.domain.enums;

/**
 * Student status in the system.
 *
 * Controls the student's lifecycle within the institution.
 *
 * @author Marcos Gustavo
 */
public enum StudentStatus {

    /**
     * Active and enrolled student.
     */
    ACTIVE("Active"),

    /**
     * Inactive student (on leave or temporarily withdrawn).
     */
    INACTIVE("Inactive"),

    /**
     * Graduated student (completed the course).
     */
    GRADUATED("Graduated"),

    /**
     * Student transferred to another institution.
     */
    TRANSFERRED("Transferred"),

    /**
     * Expelled student.
     */
    EXPELLED("Expelled");

    private final String displayName;

    StudentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if the student is academically active.
     *
     * @return true if ACTIVE
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * Checks if the student can be enrolled in classes.
     *
     * @return true if ACTIVE
     */
    public boolean canEnroll() {
        return this == ACTIVE;
    }
}