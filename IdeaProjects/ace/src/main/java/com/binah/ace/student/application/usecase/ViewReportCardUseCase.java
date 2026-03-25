package com.binah.ace.student.application.usecase;

import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.exception.StudentNotFoundException;
import com.binah.ace.student.domain.repository.GradeRepository;
import com.binah.ace.student.domain.repository.StudentRepository;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import com.binah.ace.student.domain.valueobject.GPA;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Use case: View student's report card.
 *
 * Returns the consolidated report card with:
 * - Student data
 * - Grades by subject
 * - Period GPA
 * - Attendance
 * - Approval status
 *
 * @author Marcos Gustavo
 */
@Service
public class ViewReportCardUseCase {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final CalculateGPAUseCase calculateGPAUseCase;

    public ViewReportCardUseCase(
            StudentRepository studentRepository,
            GradeRepository gradeRepository,
            CalculateGPAUseCase calculateGPAUseCase
    ) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.calculateGPAUseCase = calculateGPAUseCase;
    }

    /**
     * Generates the student's report card for a given period.
     *
     * @param studentId Student ID
     * @param period Academic period
     * @return Consolidated report card
     */
    public ReportCard execute(UUID studentId, AcademicPeriod period) {
        // 1. Fetch student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        // 2. Calculate period GPA
        GPA gpa = calculateGPAUseCase.execute(studentId, period);

        // 3. Fetch grades for the period
        List<Grade> grades = gradeRepository.findByStudentIdAndPeriod(studentId, period);

        // 4. Group grades by subject (classroomSubjectId)
        Map<UUID, List<Grade>> gradesBySubject = grades.stream()
                .collect(Collectors.groupingBy(Grade::getClassroomSubjectId));

        // 5. Calculate attendance (simulated - waiting for AttendanceRepository)
        BigDecimal attendancePercentage = BigDecimal.valueOf(85.0);

        // 6. Check approval status
        boolean isApproved = gpa.isApproved() &&
                attendancePercentage.compareTo(BigDecimal.valueOf(75)) >= 0;

        // 7. Build report card
        return new ReportCard(
                student.getId(),
                student.getFullName(),
                student.getEnrollment().enrollmentNumber(),
                period,
                gpa,
                attendancePercentage,
                isApproved,
                gradesBySubject
        );
    }

    /**
     * Record representing the report card.
     */
    public record ReportCard(
            UUID studentId,
            String studentName,
            String enrollmentNumber,
            AcademicPeriod period,
            GPA gpa,
            BigDecimal attendancePercentage,
            boolean isApproved,
            Map<UUID, List<Grade>> gradesBySubject
    ) {}
}