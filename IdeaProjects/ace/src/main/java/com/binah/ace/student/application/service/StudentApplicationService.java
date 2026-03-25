package com.binah.ace.student.application.service;

import com.binah.ace.student.application.usecase.*;
import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import com.binah.ace.student.domain.valueobject.GPA;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Application Service of the Student module.
 *
 * Orchestrates the use cases and provides a unified API
 * for the interface layer (GraphQL).
 *
 * Responsible for:
 * - Coordinating multiple use cases
 * - Managing complex transactions
 * - Providing high-level operations
 *
 * @author Marcos Gustavo
 */
@Service
public class StudentApplicationService {

    private final CreateStudentUseCase createStudentUseCase;
    private final PostGradeUseCase postGradeUseCase;
    private final CalculateGPAUseCase calculateGPAUseCase;
    private final ViewReportCardUseCase viewReportCardUseCase;

    public StudentApplicationService(
            CreateStudentUseCase createStudentUseCase,
            PostGradeUseCase postGradeUseCase,
            CalculateGPAUseCase calculateGPAUseCase,
            ViewReportCardUseCase viewReportCardUseCase
    ) {
        this.createStudentUseCase = createStudentUseCase;
        this.postGradeUseCase = postGradeUseCase;
        this.calculateGPAUseCase = calculateGPAUseCase;
        this.viewReportCardUseCase = viewReportCardUseCase;
    }

    /**
     * Creates a new student.
     */
    public Student createStudent(
            CreateStudentUseCase.CreateStudentCommand command,
            UUID createdBy
    ) {
        return createStudentUseCase.execute(command, createdBy);
    }

    /**
     * Posts a student grade.
     */
    public Grade postGrade(
            PostGradeUseCase.PostGradeCommand command,
            UUID postedBy
    ) {
        return postGradeUseCase.execute(command, postedBy);
    }

    /**
     * Calculates the student's GPA for a given period.
     */
    public GPA calculateGPA(UUID studentId, AcademicPeriod period) {
        return calculateGPAUseCase.execute(studentId, period);
    }

    /**
     * Calculates the student's overall GPA.
     */
    public GPA calculateOverallGPA(UUID studentId) {
        return calculateGPAUseCase.executeOverall(studentId);
    }

    /**
     * Views the student's report card.
     */
    public ViewReportCardUseCase.ReportCard viewReportCard(
            UUID studentId,
            AcademicPeriod period
    ) {
        return viewReportCardUseCase.execute(studentId, period);
    }

    /**
     * Composite operation: Posts a grade and automatically updates the GPA.
     *
     * Example of orchestration of multiple use cases.
     */
    public GradePostedResult postGradeAndUpdateGPA(
            PostGradeUseCase.PostGradeCommand gradeCommand,
            UUID postedBy
    ) {
        // 1. Lança nota
        Grade grade = postGradeUseCase.execute(gradeCommand, postedBy);

        // 2. Recalcula GPA do período
        AcademicPeriod period = grade.getAcademicPeriod();
        GPA updatedGPA = calculateGPAUseCase.execute(grade.getStudentId(), period);

        // 3. Retorna resultado consolidado
        return new GradePostedResult(grade, updatedGPA);
    }

    /**
     * Result of posting a grade with updated GPA.
     */
    public record GradePostedResult(
            Grade grade,
            GPA updatedGPA
    ) {}
}