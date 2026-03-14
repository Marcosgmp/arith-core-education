package com.binah.ace.teacher.application.service;

import com.binah.ace.teacher.application.usecase.AssignSubjectUseCase;
import com.binah.ace.teacher.application.usecase.CreateTeacherUseCase;
import com.binah.ace.teacher.application.usecase.UpdateTeacherUseCase;
import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.teacher.domain.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherApplicationService {
    
    private final CreateTeacherUseCase createTeacherUseCase;
    private final AssignSubjectUseCase assignSubjectUseCase;
    private final UpdateTeacherUseCase updateTeacherUseCase;
    private final TeacherRepository teacherRepository;
    
    // 🎯 MÉTODOS DE CRIAÇÃO
    public Teacher createTeacher(CreateTeacherUseCase.CreateTeacherCommand command, UUID createdBy) {
        return createTeacherUseCase.execute(command, createdBy);
    }
    
    // 🎯 MÉTODOS DE VÍNCULO
    public Teacher assignSubject(UUID teacherId, UUID subjectId) {
        return assignSubjectUseCase.execute(teacherId, subjectId);
    }
    
    // 🎯 MÉTODOS DE ATUALIZAÇÃO
    public Teacher updateTeacher(UpdateTeacherUseCase.UpdateTeacherCommand command) {
        return updateTeacherUseCase.execute(command);
    }
    
    // 🎯 MÉTODOS DE STATUS
    public Teacher activateTeacher(UUID id) {
        Teacher teacher = findById(id)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado: " + id));
        teacher.activate();
        return teacherRepository.save(teacher);
    }
    
    public Teacher inactivateTeacher(UUID id) {
        Teacher teacher = findById(id)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado: " + id));
        teacher.inactivate();
        return teacherRepository.save(teacher);
    }
    
    // 🔍 MÉTODOS DE LEITURA (delegam para repository)
    public Optional<Teacher> findById(UUID id) {
        return teacherRepository.findById(id);
    }
    
    public List<Teacher> findActiveTeachers() {
        return teacherRepository.findActiveTeachers();
    }
    
    public List<Teacher> searchByName(String name) {
        return teacherRepository.searchByName(name);
    }
    
    public List<Teacher> findBySubjectId(UUID subjectId) {
        return teacherRepository.findBySubjectId(subjectId);
    }
}
