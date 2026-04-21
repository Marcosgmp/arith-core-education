package com.binah.ace.teacher.application.usecase;

import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.teacher.domain.exception.TeacherNotFoundException;
import com.binah.ace.teacher.domain.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateTeacherUseCase {
    
    private final TeacherRepository teacherRepository;
    
    public record UpdateTeacherCommand(
        UUID teacherId,
        String fullName,
        String email,
        String phone,
        Integer workloadHours
    ) {}
    
    public Teacher execute(UpdateTeacherCommand command) {
        // 1. Buscar professor
        Teacher teacher = teacherRepository.findById(command.teacherId())
            .orElseThrow(() -> new TeacherNotFoundException(command.teacherId()));
        
        // 2. Atualizar dados pessoais
        if (command.fullName() != null || command.email() != null || command.phone() != null) {
            Email newEmail = command.email() != null ? new Email(command.email()) : null;
            teacher.updatePersonalInfo(command.fullName(), newEmail, command.phone());
        }
        
        // 3. Atualizar carga horária
        if (command.workloadHours() != null) {
            teacher.updateWorkLoad(command.workloadHours());
        }
        
        // 4. Salvar
        return teacherRepository.save(teacher);
    }
}
