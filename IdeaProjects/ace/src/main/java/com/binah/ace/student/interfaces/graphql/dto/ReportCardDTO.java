package com.binah.ace.student.interfaces.graphql.dto;

import com.binah.ace.student.application.usecase.ViewReportCardUseCase;
import com.binah.ace.student.domain.entity.Grade;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * DTO representando Report Card (boletim) para GraphQL.
 *
 * @author Marcos Gustavo
 */
public record ReportCardDTO(
        UUID studentId,
        String studentName,
        String enrollmentNumber,
        Integer year,
        Integer semester,
        Integer bimester,
        BigDecimal gpa,
        BigDecimal attendancePercentage,
        Boolean isApproved,
        List<SubjectGradesDTO> subjectGrades
) {
    /**
     * Converts ReportCard from the use case to DTO.
     */
    public static ReportCardDTO from(ViewReportCardUseCase.ReportCard reportCard) {
        // Converte mapa de notas por disciplina em lista de SubjectGradesDTO
        List<SubjectGradesDTO> subjectGrades = reportCard.gradesBySubject()
                .entrySet()
                .stream()
                .map(entry -> new SubjectGradesDTO(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(GradeDTO::from)
                                .collect(Collectors.toList()),
                        calculateSubjectAverage(entry.getValue())
                ))
                .collect(Collectors.toList());

        return new ReportCardDTO(
                reportCard.studentId(),
                reportCard.studentName(),
                reportCard.enrollmentNumber(),
                reportCard.period().year(),
                reportCard.period().semester(),
                reportCard.period().bimester(),
                reportCard.gpa().value(),
                reportCard.attendancePercentage(),
                reportCard.isApproved(),
                subjectGrades
        );
    }

    /**
     * Calculates the average for a subject.
     */
    private static BigDecimal calculateSubjectAverage(List<Grade> grades) {
        if (grades.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = grades.stream()
                .map(Grade::getScore)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(
                BigDecimal.valueOf(grades.size()),
                2,
                java.math.RoundingMode.HALF_UP
        );
    }

    /**
     * DTO representing the grades of a subject.
     */
    public record SubjectGradesDTO(
            UUID subjectId,
            List<GradeDTO> grades,
            BigDecimal average
    ) {}
}