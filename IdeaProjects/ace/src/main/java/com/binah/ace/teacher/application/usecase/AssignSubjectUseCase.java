package com.binah.ace.teacher.application.usecase;

import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.teacher.domain.exception.TeacherNotFoundException;
import com.binah.ace.teacher.domain.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssignSubjectUseCase {
    
    private final TeacherRepository teacherRepository;
    
    public Teacher execute(UUID teacherId, UUID subjectId) {
        // 1. Buscar professor ou lançar exceção
        Teacher teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new TeacherNotFoundException(teacherId));
        
        // 2. Validar se professor pode ensinar
        if (!teacher.canTeach()) {
            throw new RuntimeException("Professor não está ativo para vincular disciplinas");
        }
        
        // 3. Executar regra de negócio
        teacher.assignSubject(subjectId);
        
        // 4. Salvar e retornar
        return teacherRepository.save(teacher);
    }
}
