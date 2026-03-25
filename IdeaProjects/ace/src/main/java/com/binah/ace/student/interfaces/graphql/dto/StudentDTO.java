package com.binah.ace.student.interfaces.graphql.dto;

import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.enums.StudentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO representing Student for GraphQL.
 *
 * Exposes only safe data (no sensitive information).
 *
 * @author Marcos Gustavo
 */
public record StudentDTO(
        UUID id,
        String fullName,
        String cpf,
        String email,
        LocalDate birthDate,
        String phone,
        String address,
        String enrollmentNumber,
        LocalDate enrollmentDate,
        StudentStatus status,
        String guardianName,
        String guardianPhone,
        String guardianEmail,
        Integer age,
        Boolean isMinor,
        LocalDateTime createdAt
) {
    /**
     * Converts Student domain to DTO.
     */
    public static StudentDTO from(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFullName(),
                student.getCpf().value(),
                student.getEmail().value(),
                student.getBirthDate(),
                student.getPhone(),
                student.getAddress(),
                student.getEnrollment() != null ? student.getEnrollment().enrollmentNumber() : null,
                student.getEnrollmentDate(),
                student.getStatus(),
                student.getGuardianName(),
                student.getGuardianPhone(),
                student.getGuardianEmail().value(),
                student.getAge(),
                student.isMinor(),
                student.getCreatedAt()
        );
    }
}