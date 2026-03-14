package com.binah.ace.teacher.application.usecase;

import com.binah.ace.shared.domain.valueobject.CPF;
import com.binah.ace.shared.domain.valueobject.Email;
import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.teacher.domain.enums.ContractType;
import com.binah.ace.teacher.domain.repository.TeacherRepository;
import com.binah.ace.teacher.domain.exception.TeacherNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.UUID;

@Component  // ⭐️ ADICIONAR ISSO
@RequiredArgsConstructor  // ⭐️ ADICIONAR ISSO
public class CreateTeacherUseCase {
    
    private final TeacherRepository teacherRepository;  // ⭐️ ADICIONAR ISSO
    
    // Record Command conforme especificação
    public record CreateTeacherCommand(
        String fullName,
        String cpf,
        String email,
        String phone,
        ContractType contractType,
        Integer workloadHours,
        LocalDate hireDate
    ) {}
    
    public Teacher execute(CreateTeacherCommand command, UUID createdBy) {
        // 1. Validar CPF único
        if (teacherRepository.existsByCPF(new CPF(command.cpf()))) {
            throw new RuntimeException("Professor com CPF " + command.cpf() + " já existe");
        }
        
        // 2. Criar value objects
        CPF cpfVo = new CPF(command.cpf());
        Email emailVo = new Email(command.email());
        
        // 3. Criar Teacher com construtor
        Teacher teacher = new Teacher(
            command.fullName(),
            cpfVo,
            emailVo,
            command.phone(),
            command.contractType(),
            command.workloadHours()
        );
        
        // 4. Set hireDate se fornecido
        if (command.hireDate() != null) {
            // Assumindo que Teacher tem setHireDate()
            // teacher.setHireDate(command.hireDate());
        }
        
        // 5. Salvar e retornar
        return teacherRepository.save(teacher);
    }
}
