package com.binah.ace.student.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA entity for AcademicHistory persistence.
 *
 * @author Marcos Gustavo
 */
@Entity
@Table(name = "academic_history",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"student_id", "year", "semester", "bimester"}
        )
)
@Getter
@Setter
public class AcademicHistoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    // Academic period
    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer semester;

    private Integer bimester;

    // Performance
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal gpa;

    @Column(name = "attendance_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal attendancePercentage;

    @Column(name = "total_classes", nullable = false)
    private Integer totalClasses;

    @Column(name = "attended_classes", nullable = false)
    private Integer attendedClasses;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits;

    @Column(name = "approved_credits", nullable = false)
    private Integer approvedCredits;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (gpa == null) gpa = BigDecimal.ZERO;
        if (attendancePercentage == null) attendancePercentage = BigDecimal.ZERO;
        if (totalClasses == null) totalClasses = 0;
        if (attendedClasses == null) attendedClasses = 0;
        if (totalCredits == null) totalCredits = 0;
        if (approvedCredits == null) approvedCredits = 0;
        if (isApproved == null) isApproved = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}