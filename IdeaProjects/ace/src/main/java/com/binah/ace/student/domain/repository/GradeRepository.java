package com.binah.ace.student.domain.repository;

import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Persistence contract for the Grade entity.
 *
 * PURE domain interface - WITHOUT JPA dependency.
 *
 * @author Marcos Gustavo
 */
public interface GradeRepository {

    Optional<Grade> findById(UUID id);

    List<Grade> findByStudentId(UUID studentId);

    List<Grade> findByStudentIdAndPeriod(UUID studentId, AcademicPeriod period);

    List<Grade> findByClassroomSubjectId(UUID classroomSubjectId);

    Grade save(Grade grade);

    void deleteById(UUID id);
}