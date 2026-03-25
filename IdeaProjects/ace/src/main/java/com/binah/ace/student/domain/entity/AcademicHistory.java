package com.binah.ace.student.domain.entity;

import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import com.binah.ace.student.domain.valueobject.GPA;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity representing Academic History.
 *
 * Consolidates the student's performance for a specific period.
 * Calculated based on grades and attendance during the period.
 *
 * @author Marcos Gustavo
 */
@Getter
public class AcademicHistory {

    private final UUID id;
    private final UUID studentId;
    private final AcademicPeriod academicPeriod;

    // Desempenho
    private GPA gpa;                           // Média geral do período
    private BigDecimal attendancePercentage;   // Percentual de presença
    private int totalClasses;                  // Total de aulas
    private int attendedClasses;               // Aulas presentes
    private int totalCredits;                  // Créditos cursados
    private int approvedCredits;               // Créditos aprovados

    // Status consolidado
    private boolean isApproved;                // Aprovado no período?
    private String remarks;                    // Observações gerais

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor to create a new academic history.
     */
    public AcademicHistory(
            UUID id,
            UUID studentId,
            AcademicPeriod academicPeriod
    ) {
        this.id = id;
        this.studentId = studentId;
        this.academicPeriod = academicPeriod;
        this.gpa = GPA.of(0.0);
        this.attendancePercentage = BigDecimal.ZERO;
        this.totalClasses = 0;
        this.attendedClasses = 0;
        this.totalCredits = 0;
        this.approvedCredits = 0;
        this.isApproved = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Full constructor (database reconstruction).
     */
    public AcademicHistory(
            UUID id,
            UUID studentId,
            AcademicPeriod academicPeriod,
            GPA gpa,
            BigDecimal attendancePercentage,
            int totalClasses,
            int attendedClasses,
            int totalCredits,
            int approvedCredits,
            boolean isApproved,
            String remarks,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.studentId = studentId;
        this.academicPeriod = academicPeriod;
        this.gpa = gpa;
        this.attendancePercentage = attendancePercentage;
        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;
        this.totalCredits = totalCredits;
        this.approvedCredits = approvedCredits;
        this.isApproved = isApproved;
        this.remarks = remarks;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // BUSINESS RULES

    /**
     * Updates the GPA for the period.
     */
    public void updateGPA(GPA newGpa) {
        this.gpa = newGpa;
        this.updatedAt = LocalDateTime.now();
        recalculateApprovalStatus();
    }

    /**
     * Updates attendance statistics.
     */
    public void updateAttendance(int totalClasses, int attendedClasses) {
        if (totalClasses < 0 || attendedClasses < 0) {
            throw new IllegalArgumentException("Class counts cannot be negative");
        }

        if (attendedClasses > totalClasses) {
            throw new IllegalArgumentException(
                    "Attended classes cannot exceed total classes"
            );
        }

        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;

        // Calcula percentual
        if (totalClasses > 0) {
            this.attendancePercentage = BigDecimal.valueOf(attendedClasses)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(totalClasses), 2, java.math.RoundingMode.HALF_UP);
        } else {
            this.attendancePercentage = BigDecimal.ZERO;
        }

        this.updatedAt = LocalDateTime.now();
        recalculateApprovalStatus();
    }

    /**
     * Updates credit statistics.
     */
    public void updateCredits(int totalCredits, int approvedCredits) {
        if (totalCredits < 0 || approvedCredits < 0) {
            throw new IllegalArgumentException("Credits cannot be negative");
        }

        if (approvedCredits > totalCredits) {
            throw new IllegalArgumentException(
                    "Approved credits cannot exceed total credits"
            );
        }

        this.totalCredits = totalCredits;
        this.approvedCredits = approvedCredits;
        this.updatedAt = LocalDateTime.now();
        recalculateApprovalStatus();
    }

    /**
     * Adds remarks to the academic history.
     */
    public void addRemarks(String remarks) {
        this.remarks = remarks;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Recalculates whether the student was approved for the period.
     *
     * Criteria:
     * - GPA >= 7.0
     * - Attendance >= 75%
     */
    private void recalculateApprovalStatus() {
        boolean hasMinimumGPA = gpa.isApproved();
        boolean hasMinimumAttendance = attendancePercentage
                .compareTo(BigDecimal.valueOf(75)) >= 0;

        this.isApproved = hasMinimumGPA && hasMinimumAttendance;
    }

    /**
     * Checks if the student is at academic risk.
     *
     * @return true if GPA < 5.0 or attendance < 50%
     */
    public boolean isAtRisk() {
        boolean lowGPA = gpa.isFailed();
        boolean lowAttendance = attendancePercentage
                .compareTo(BigDecimal.valueOf(50)) < 0;

        return lowGPA || lowAttendance;
    }

    /**
     * Checks if the student needs recovery.
     */
    public boolean needsRecovery() {
        return gpa.needsRecovery() && attendancePercentage
                .compareTo(BigDecimal.valueOf(75)) >= 0;
    }
}