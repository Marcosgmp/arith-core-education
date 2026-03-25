package com.binah.ace.student.infrastructure.persistence.jpa;

import com.binah.ace.student.domain.enums.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for StudentJpaEntity.
 *
 * @author Marcos Gustavo
 */
@Repository
public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, UUID> {

    Optional<StudentJpaEntity> findByCpf(String cpf);

    Optional<StudentJpaEntity> findByEnrollmentNumber(String enrollmentNumber);

    List<StudentJpaEntity> findByStatus(StudentStatus status);

    boolean existsByCpf(String cpf);

    boolean existsByEnrollmentNumber(String enrollmentNumber);

    @Query("SELECT s FROM StudentJpaEntity s WHERE s.status = 'ACTIVE'")
    List<StudentJpaEntity> findActiveStudents();

    @Query("SELECT COUNT(s) FROM StudentJpaEntity s WHERE s.status = 'ACTIVE'")
    long countActiveStudents();

    @Query("SELECT s FROM StudentJpaEntity s WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<StudentJpaEntity> searchByName(String name);
}