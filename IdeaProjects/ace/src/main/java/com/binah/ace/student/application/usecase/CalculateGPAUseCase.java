package com.binah.ace.student.application.usecase;

import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.exception.StudentNotFoundException;
import com.binah.ace.student.domain.repository.GradeRepository;
import com.binah.ace.student.domain.repository.StudentRepository;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import com.binah.ace.student.domain.valueobject.GPA;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

/**
 * Use case: Calculate the student's GPA (Grade Point Average).
 *
 * Calculates the weighted average of the student's grades for a specific period.
 *
 * Formula:
 * GPA = Σ(grade × weight) / Σ(weight)
 *
 * @author Marcos Gustavo
 */
@Service
public class CalculateGPAUseCase {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public CalculateGPAUseCase(
            StudentRepository studentRepository,
            GradeRepository gradeRepository
    ) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    /**
     * Calculates the student's GPA for a given period.
     *
     * @param studentId Student ID
     * @param period Academic period
     * @return Calculated GPA
     */
    public GPA execute(UUID studentId, AcademicPeriod period) {
        // 1. Validate if the student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        // 2. Fetch all student grades for the period
        List<Grade> grades = gradeRepository.findByStudentIdAndPeriod(studentId, period);

        // 3. Calculate GPA (weighted average)
        return calculateWeightedAverage(grades);
    }

    /**
     * Calculates the student's overall GPA (all periods).
     *
     * @param studentId Student ID
     * @return Overall GPA
     */
    public GPA executeOverall(UUID studentId) {
        // Validate student
        studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        // Fetch all student grades
        List<Grade> allGrades = gradeRepository.findByStudentId(studentId);

        return calculateWeightedAverage(allGrades);
    }

    /**
     * Calculates the weighted average of a list of grades.
     *
     * Formula: Σ(grade × weight) / Σ(weight)
     */
    private GPA calculateWeightedAverage(List<Grade> grades) {
        if (grades.isEmpty()) {
            return GPA.of(0.0);
        }

        BigDecimal totalWeightedScore = BigDecimal.ZERO;
        BigDecimal totalWeight = BigDecimal.ZERO;

        for (Grade grade : grades) {
            BigDecimal weightedScore = grade.getWeightedScore();
            totalWeightedScore = totalWeightedScore.add(weightedScore);
            totalWeight = totalWeight.add(grade.getWeight());
        }

        if (totalWeight.compareTo(BigDecimal.ZERO) == 0) {
            return GPA.of(0.0);
        }

        BigDecimal average = totalWeightedScore
                .divide(totalWeight, 2, RoundingMode.HALF_UP);

        return new GPA(average);
    }
}