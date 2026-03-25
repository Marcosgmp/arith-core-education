package com.binah.ace.student.domain.entity;

import com.binah.ace.student.domain.enums.AssessmentType;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity representing a Grade.
 *
 * Represents a grade obtained by a student in a specific assessment.
 *
 * @author Marcos Gustavo
 */
@Getter
public class Grade {

    private final UUID id;
    private final UUID studentId;
    private final UUID classroomSubjectId;  // Relationship with classroom + subject
    private final AssessmentType assessmentType;
    private final AcademicPeriod academicPeriod;

    private BigDecimal score;              // Score (0.00 to 10.00)
    private BigDecimal weight;             // Assessment weight (default 1.0)
    private String description;            // Example: "Bimonthly Exam", "Group Project"
    private LocalDate assessmentDate;      // Assessment date

    private String comments;               // Teacher comments
    private final UUID postedBy;           // ID of the teacher who posted the grade

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor to create a new grade.
     */
    public Grade(
            UUID id,
            UUID studentId,
            UUID classroomSubjectId,
            AssessmentType assessmentType,
            AcademicPeriod academicPeriod,
            BigDecimal score,
            BigDecimal weight,
            String description,
            LocalDate assessmentDate,
            UUID postedBy
    ) {
        validateScore(score);
        validateWeight(weight);

        this.id = id;
        this.studentId = studentId;
        this.classroomSubjectId = classroomSubjectId;
        this.assessmentType = assessmentType;
        this.academicPeriod = academicPeriod;
        this.score = score;
        this.weight = weight != null ? weight : BigDecimal.ONE;
        this.description = description;
        this.assessmentDate = assessmentDate;
        this.postedBy = postedBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Full constructor (database reconstruction).
     */
    public Grade(
            UUID id,
            UUID studentId,
            UUID classroomSubjectId,
            AssessmentType assessmentType,
            AcademicPeriod academicPeriod,
            BigDecimal score,
            BigDecimal weight,
            String description,
            LocalDate assessmentDate,
            String comments,
            UUID postedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.studentId = studentId;
        this.classroomSubjectId = classroomSubjectId;
        this.assessmentType = assessmentType;
        this.academicPeriod = academicPeriod;
        this.score = score;
        this.weight = weight;
        this.description = description;
        this.assessmentDate = assessmentDate;
        this.comments = comments;
        this.postedBy = postedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // BUSINESS RULES

    /**
     * Updates the grade score.
     *
     * @param newScore New score
     * @throws IllegalArgumentException if the score is invalid
     */
    public void updateScore(BigDecimal newScore) {
        validateScore(newScore);
        this.score = newScore;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Adds teacher comments.
     */
    public void addComments(String comments) {
        this.comments = comments;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Calculates the weighted score (score * weight).
     */
    public BigDecimal getWeightedScore() {
        return score.multiply(weight);
    }

    /**
     * Checks if the grade is passing (>= 7.0).
     */
    public boolean isPassing() {
        return score.compareTo(BigDecimal.valueOf(7.0)) >= 0;
    }

    /**
     * Checks if the grade is failing (< 5.0).
     */
    public boolean isFailing() {
        return score.compareTo(BigDecimal.valueOf(5.0)) < 0;
    }

    /**
     * Validates if the score is within the valid range (0.00 to 10.00).
     */
    private void validateScore(BigDecimal score) {
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }

        if (score.compareTo(BigDecimal.ZERO) < 0 ||
                score.compareTo(BigDecimal.TEN) > 0) {
            throw new IllegalArgumentException(
                    "Score must be between 0.00 and 10.00, got: " + score
            );
        }
    }

    /**
     * Validates if the weight is valid (> 0).
     */
    private void validateWeight(BigDecimal weight) {
        if (weight != null && weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Weight must be greater than 0, got: " + weight
            );
        }
    }
}