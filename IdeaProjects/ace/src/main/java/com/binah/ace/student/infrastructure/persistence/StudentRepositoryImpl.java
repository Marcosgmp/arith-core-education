package com.binah.ace.student.infrastructure.persistence;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.enums.StudentStatus;
import com.binah.ace.student.domain.repository.StudentRepository;
import com.binah.ace.student.domain.valueobject.Enrollment;
import com.binah.ace.student.infrastructure.persistence.jpa.StudentJpaEntity;
import com.binah.ace.student.infrastructure.persistence.jpa.StudentJpaRepository;
import com.binah.ace.student.infrastructure.persistence.mapper.StudentMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of StudentRepository using JPA.
 *
 * Bridge between Domain and Infrastructure.
 *
 * @author Marcos Gustavo
 */
@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentJpaRepository jpaRepository;
    private final StudentMapper mapper;

    public StudentRepositoryImpl(
            StudentJpaRepository jpaRepository,
            StudentMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Student> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Student> findByCPF(CPF cpf) {
        return jpaRepository.findByCpf(cpf.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Student> findByEnrollmentNumber(Enrollment enrollment) {
        return jpaRepository.findByEnrollmentNumber(enrollment.enrollmentNumber())
                .map(mapper::toDomain);
    }

    @Override
    public List<Student> findByStatus(StudentStatus status) {
        return jpaRepository.findByStatus(status)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findActiveStudents() {
        return jpaRepository.findActiveStudents()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCPF(CPF cpf) {
        return jpaRepository.existsByCpf(cpf.value());
    }

    @Override
    public boolean existsByEnrollmentNumber(Enrollment enrollment) {
        return jpaRepository.existsByEnrollmentNumber(enrollment.enrollmentNumber());
    }

    @Override
    public Student save(Student student) {
        StudentJpaEntity entity = mapper.toJpa(student);
        StudentJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public long countActiveStudents() {
        return jpaRepository.countActiveStudents();
    }

    @Override
    public List<Student> searchByName(String name) {
        return jpaRepository.searchByName(name)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}