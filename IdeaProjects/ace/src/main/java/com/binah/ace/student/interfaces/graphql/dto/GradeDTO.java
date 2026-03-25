package com.binah.ace.student.interfaces.graphql.dto;

import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.enums.AssessmentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO representing Grade for GraphQL.
 *
 * @author Marcos Gustavo
 */
public record GradeDTO(
        UUID id,
        UUID studentId,
        UUID classroomSubjectId,
        AssessmentType assessmentType,
        BigDecimal score,
        BigDecimal weight,
        String description,
        LocalDate assessmentDate,
        String comments,
        Integer year,
        Integer semester,
        Integer bimester,
        Boolean isPassing,
        Boolean isFailing
) {
    /**
     * Converts Grade domain to DTO.
     */
    public static GradeDTO from(Grade grade) {
        return new GradeDTO(
                grade.getId(),
                grade.getStudentId(),
                grade.getClassroomSubjectId(),
                grade.getAssessmentType(),
                grade.getScore(),
                grade.getWeight(),
                grade.getDescription(),
                grade.getAssessmentDate(),
                grade.getComments(),
                grade.getAcademicPeriod().year(),
                grade.getAcademicPeriod().semester(),
                grade.getAcademicPeriod().bimester(),
                grade.isPassing(),
                grade.isFailing()
        );
    }
}