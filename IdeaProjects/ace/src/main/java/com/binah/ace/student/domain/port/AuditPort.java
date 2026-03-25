package com.binah.ace.student.domain.port;

import java.util.UUID;

/**
 * Output port for the audit service.
 *
 * Defines the contract that the Student module expects
 * from an external audit service.
 *
 * Implementation is located in Infrastructure (adapter).
 *
 * @author Marcos Gustavo
 */
public interface AuditPort {


    /**
     * Records student creation.
     *
     * @param studentId ID of the created student
     * @param createdBy ID of the user who created it
     * @param enrollmentNumber Enrollment number
     */
    void recordStudentCreated(UUID studentId, UUID createdBy, String enrollmentNumber);

    /**
     * Records grade posting.
     *
     * @param gradeId ID of the grade
     * @param studentId ID of the student
     * @param postedBy ID of the teacher
     * @param score Posted score
     */
    void recordGradePosted(UUID gradeId, UUID studentId, UUID postedBy, double score);

    /**
     * Records grade update.
     *
     * @param gradeId ID of the grade
     * @param oldScore Previous score
     * @param newScore New score
     * @param updatedBy ID of the user who updated it
     */
    void recordGradeUpdated(
            UUID gradeId,
            double oldScore,
            double newScore,
            UUID updatedBy
    );

    /**
     * Records student status change.
     *
     * @param studentId ID of the student
     * @param oldStatus Previous status
     * @param newStatus New status
     * @param changedBy ID of the user who changed it
     * @param reason Reason for the change
     */
    void recordStatusChanged(
            UUID studentId,
            String oldStatus,
            String newStatus,
            UUID changedBy,
            String reason
    );

    /**
     * Records course completion (graduation).
     *
     * @param studentId ID of the student
     * @param finalGPA Final GPA
     */
    void recordGraduation(UUID studentId, double finalGPA);
}