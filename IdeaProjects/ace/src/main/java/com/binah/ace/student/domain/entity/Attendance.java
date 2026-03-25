package com.binah.ace.student.domain.entity;

import com.binah.ace.student.domain.enums.AttendanceStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity representing Attendance.
 *
 * Records the presence or absence of a student in a specific class session.
 *
 * @author Marcos Gustavo
 */
@Getter
public class Attendance {

    private final UUID id;
    private final UUID studentId;
    private final UUID classroomSubjectId;  // Class + Subject
    private final LocalDate classDate;       // Class date

    private AttendanceStatus status;
    private String notes;                    // Notes (e.g., reason for absence)
    private final UUID recordedBy;           // Teacher who recorded the attendance

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor to create a new attendance record.
     */
    public Attendance(
            UUID id,
            UUID studentId,
            UUID classroomSubjectId,
            LocalDate classDate,
            AttendanceStatus status,
            UUID recordedBy
    ) {
        validateClassDate(classDate);

        this.id = id;
        this.studentId = studentId;
        this.classroomSubjectId = classroomSubjectId;
        this.classDate = classDate;
        this.status = status;
        this.recordedBy = recordedBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Full constructor (database reconstruction).
     */
    public Attendance(
            UUID id,
            UUID studentId,
            UUID classroomSubjectId,
            LocalDate classDate,
            AttendanceStatus status,
            String notes,
            UUID recordedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.studentId = studentId;
        this.classroomSubjectId = classroomSubjectId;
        this.classDate = classDate;
        this.status = status;
        this.notes = notes;
        this.recordedBy = recordedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // BUSINESS RULES

    /**
     * Updates the attendance status.
     *
     * @param newStatus New status
     */
    public void updateStatus(AttendanceStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Adds or updates notes.
     */
    public void addNotes(String notes) {
        this.notes = notes;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Justifies an absence.
     *
     * @param justification Reason for the absence
     * @throws IllegalStateException if status is not ABSENT
     */
    public void justify(String justification) {
        if (this.status != AttendanceStatus.ABSENT) {
            throw new IllegalStateException("Can only justify absences");
        }

        this.status = AttendanceStatus.EXCUSED;
        this.notes = justification;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Checks if it counts as presence.
     */
    public boolean countsAsPresent() {
        return status.countsAsPresent();
    }

    /**
     * Checks if it is an unexcused absence.
     */
    public boolean isUnexcusedAbsence() {
        return status.isUnexcusedAbsence();
    }

    /**
     * Validates that the class date is not in the future.
     */
    private void validateClassDate(LocalDate classDate) {
        if (classDate == null) {
            throw new IllegalArgumentException("Class date cannot be null");
        }

        if (classDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Cannot record attendance for future classes: " + classDate
            );
        }
    }
}