package com.binah.ace.student.infrastructure.persistence.mapper;

import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import com.binah.ace.student.infrastructure.persistence.jpa.GradeJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting Grade (domain) ↔ GradeJpaEntity (infrastructure).
 *
 * @author Marcos Gustavo
 */
@Component
public class GradeMapper {

    /**
     * JPA Entity → Domain Entity.
     */
    public Grade toDomain(GradeJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        AcademicPeriod period = entity.getBimester() != null
                ? AcademicPeriod.of(entity.getYear(), entity.getSemester(), entity.getBimester())
                : AcademicPeriod.of(entity.getYear(), entity.getSemester());

        return new Grade(
                entity.getId(),
                entity.getStudentId(),
                entity.getClassroomSubjectId(),
                entity.getAssessmentType(),
                period,
                entity.getScore(),
                entity.getWeight(),
                entity.getDescription(),
                entity.getAssessmentDate(),
                entity.getComments(),
                entity.getPostedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Domain Entity → JPA Entity.
     */
    public GradeJpaEntity toJpa(Grade domain) {
        if (domain == null) {
            return null;
        }

        GradeJpaEntity entity = new GradeJpaEntity();
        entity.setId(domain.getId());
        entity.setStudentId(domain.getStudentId());
        entity.setClassroomSubjectId(domain.getClassroomSubjectId());
        entity.setAssessmentType(domain.getAssessmentType());
        entity.setYear(domain.getAcademicPeriod().year());
        entity.setSemester(domain.getAcademicPeriod().semester());
        entity.setBimester(domain.getAcademicPeriod().bimester());
        entity.setScore(domain.getScore());
        entity.setWeight(domain.getWeight());
        entity.setDescription(domain.getDescription());
        entity.setAssessmentDate(domain.getAssessmentDate());
        entity.setComments(domain.getComments());
        entity.setPostedBy(domain.getPostedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }
}