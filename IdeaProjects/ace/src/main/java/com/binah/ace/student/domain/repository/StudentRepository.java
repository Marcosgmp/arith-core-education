package com.binah.ace.student.domain.repository;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.enums.StudentStatus;
import com.binah.ace.student.domain.valueobject.Enrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Persistence contract for the Student entity.
 *
 * PURE domain interface - WITHOUT dependency on JPA.
 * Implementation is located in the Infrastructure layer.
 *
 * @author Marcos Gustavo
 */
public interface StudentRepository {

    /**
     * Finds a student by ID.
     */
    Optional<Student> findById(UUID id);

    /**
     * Finds a student by CPF.
     */
    Optional<Student> findByCPF(CPF cpf);

    /**
     * Finds a student by enrollment number.
     */
    Optional<Student> findByEnrollmentNumber(Enrollment enrollment);

    /**
     * Finds students by status.
     */
    List<Student> findByStatus(StudentStatus status);

    /**
     * Finds active students.
     */
    List<Student> findActiveStudents();

    /**
     * Checks if a CPF already exists.
     */
    boolean existsByCPF(CPF cpf);

    /**
     * Checks if an enrollment number already exists.
     */
    boolean existsByEnrollmentNumber(Enrollment enrollment);

    /**
     * Saves a student (create or update).
     */
    Student save(Student student);

    /**
     * Deletes a student.
     */
    void deleteById(UUID id);

    /**
     * Counts the total number of active students.
     */
    long countActiveStudents();

    /**
     * Searches students by name (partial match).
     */
    List<Student> searchByName(String name);
}