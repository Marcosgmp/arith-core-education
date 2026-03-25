package com.binah.ace.student.infrastructure.persistence.mapper;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.valueobject.Enrollment;
import com.binah.ace.student.infrastructure.persistence.jpa.StudentJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting Student (domain) ↔ StudentJpaEntity (infrastructure).
 *
 * @author Marcos Gustavo
 */
@Component
public class StudentMapper {

    /**
     * JPA Entity → Domain Entity.
     */
    public Student toDomain(StudentJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        Enrollment enrollment = null;
        if (entity.getEnrollmentNumber() != null && entity.getEnrollmentDate() != null) {
            enrollment = Enrollment.of(
                    entity.getEnrollmentNumber(),
                    entity.getEnrollmentDate()
            );
        }

        return new Student(
                entity.getId(),
                entity.getFullName(),
                new CPF(entity.getCpf()),
                new Email(entity.getEmail()),
                entity.getBirthDate(),
                entity.getPhone(),
                entity.getAddress(),
                enrollment,
                entity.getStatus(),
                entity.getEnrollmentDate(),
                entity.getGuardianName(),
                entity.getGuardianPhone(),
                new Email(entity.getGuardianEmail()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Domain Entity → JPA Entity.
     */
    public StudentJpaEntity toJpa(Student domain) {
        if (domain == null) {
            return null;
        }

        StudentJpaEntity entity = new StudentJpaEntity();
        entity.setId(domain.getId());
        entity.setFullName(domain.getFullName());
        entity.setCpf(domain.getCpf().value());
        entity.setEmail(domain.getEmail().value());
        entity.setBirthDate(domain.getBirthDate());
        entity.setPhone(domain.getPhone());
        entity.setAddress(domain.getAddress());
        entity.setStatus(domain.getStatus());
        entity.setGuardianName(domain.getGuardianName());
        entity.setGuardianPhone(domain.getGuardianPhone());
        entity.setGuardianEmail(domain.getGuardianEmail().value());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        if (domain.getEnrollment() != null) {
            entity.setEnrollmentNumber(domain.getEnrollment().enrollmentNumber());
            entity.setEnrollmentDate(domain.getEnrollment().enrollmentDate());
        }

        return entity;
    }
}