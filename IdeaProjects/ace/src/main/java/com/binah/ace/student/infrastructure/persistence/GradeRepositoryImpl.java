package com.binah.ace.student.infrastructure.persistence;

import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.repository.GradeRepository;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import com.binah.ace.student.infrastructure.persistence.jpa.GradeJpaEntity;
import com.binah.ace.student.infrastructure.persistence.jpa.GradeJpaRepository;
import com.binah.ace.student.infrastructure.persistence.mapper.GradeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of GradeRepository using JPA.
 *
 * @author Marcos Gustavo
 */
@Repository
public class GradeRepositoryImpl implements GradeRepository {

    private final GradeJpaRepository jpaRepository;
    private final GradeMapper mapper;

    public GradeRepositoryImpl(
            GradeJpaRepository jpaRepository,
            GradeMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Grade> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Grade> findByStudentId(UUID studentId) {
        return jpaRepository.findByStudentId(studentId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> findByStudentIdAndPeriod(UUID studentId, AcademicPeriod period) {
        List<GradeJpaEntity> entities;

        if (period.hasBimester()) {
            entities = jpaRepository.findByStudentIdAndPeriodWithBimester(
                    studentId,
                    period.year(),
                    period.semester(),
                    period.bimester()
            );
        } else {
            entities = jpaRepository.findByStudentIdAndPeriod(
                    studentId,
                    period.year(),
                    period.semester()
            );
        }

        return entities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> findByClassroomSubjectId(UUID classroomSubjectId) {
        return jpaRepository.findByClassroomSubjectId(classroomSubjectId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Grade save(Grade grade) {
        GradeJpaEntity entity = mapper.toJpa(grade);
        GradeJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}