package com.binah.ace.student.domain.enums;

/**
 * Student attendance status for a class.
 *
 * @author Marcos Gustavo
 */
public enum AttendanceStatus {

    /**
     * Student present.
     */
    PRESENT("Present"),

    /**
     * Student absent (unexcused absence).
     */
    ABSENT("Absent"),

    /**
     * Student arrived late.
     */
    LATE("Late"),

    /**
     * Excused absence.
     */
    EXCUSED("Excused"),

    /**
     * Medical leave.
     */
    MEDICAL_LEAVE("Medical Leave");

    private final String displayName;

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if it counts as attendance.
     *
     * @return true if PRESENT or LATE
     */
    public boolean countsAsPresent() {
        return this == PRESENT || this == LATE;
    }

    /**
     * Checks if it is an unexcused absence.
     *
     * @return true if ABSENT
     */
    public boolean isUnexcusedAbsence() {
        return this == ABSENT;
    }
}