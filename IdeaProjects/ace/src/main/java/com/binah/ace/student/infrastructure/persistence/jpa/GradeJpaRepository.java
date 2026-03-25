package com.binah.ace.student.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for GradeJpaEntity.
 *
 * @author Marcos Gustavo
 */
@Repository
public interface GradeJpaRepository extends JpaRepository<GradeJpaEntity, UUID> {

    List<GradeJpaEntity> findByStudentId(UUID studentId);

    List<GradeJpaEntity> findByClassroomSubjectId(UUID classroomSubjectId);

    @Query("SELECT g FROM GradeJpaEntity g WHERE g.studentId = :studentId " +
            "AND g.year = :year AND g.semester = :semester")
    List<GradeJpaEntity> findByStudentIdAndPeriod(
            @Param("studentId") UUID studentId,
            @Param("year") int year,
            @Param("semester") int semester
    );

    @Query("SELECT g FROM GradeJpaEntity g WHERE g.studentId = :studentId " +
            "AND g.year = :year AND g.semester = :semester AND g.bimester = :bimester")
    List<GradeJpaEntity> findByStudentIdAndPeriodWithBimester(
            @Param("studentId") UUID studentId,
            @Param("year") int year,
            @Param("semester") int semester,
            @Param("bimester") int bimester
    );
}