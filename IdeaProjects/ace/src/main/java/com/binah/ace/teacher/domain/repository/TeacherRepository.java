package com.binah.ace.teacher.domain.repository;

import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.teacher.domain.enums.TeacherStatus;

import java.util.Optional;
import java.util.UUID;
import java.util.List;


public interface TeacherRepository {

    // buscas

    Optional<Teacher> findById(UUID id);
    Optional<Teacher> findByCPF(CPF cpf);
    List<Teacher> findByStatus(TeacherStatus teacherStatus);
    List<Teacher> findActiveTeachers();


    // verificações

    boolean existsByCPF(CPF cpf);

    // Escrita
    Teacher save(Teacher teacher);
    void deleteById(UUID id);

    // buscas avancadas
    List<Teacher> searchByName(String Name);
    List<Teacher> findBySubjectId(UUID subjectId);
}
