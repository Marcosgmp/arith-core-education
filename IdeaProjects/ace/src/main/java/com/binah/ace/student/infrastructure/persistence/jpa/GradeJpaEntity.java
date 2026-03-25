package com.binah.ace.student.infrastructure.persistence.jpa;

import com.binah.ace.student.domain.enums.AssessmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA entity for Grade persistence.
 *
 * @author Marcos Gustavo
 */
@Entity
@Table(name = "grades")
@Getter
@Setter
public class GradeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "classroom_subject_id", nullable = false)
    private UUID classroomSubjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_type", nullable = false, length = 30)
    private AssessmentType assessmentType;

    // Período acadêmico (desnormalizado para performance)
    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer semester;

    private Integer bimester;

    // Nota
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal score;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal weight;

    @Column(length = 200)
    private String description;

    @Column(name = "assessment_date", nullable = false)
    private LocalDate assessmentDate;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(name = "posted_by", nullable = false)
    private UUID postedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (weight == null) {
            weight = BigDecimal.ONE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}