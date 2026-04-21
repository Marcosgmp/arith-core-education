package com.binah.ace.teacher.application.usecase;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.teacher.domain.enums.ContractType;
import com.binah.ace.teacher.domain.repository.TeacherRepository;
import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Application use case responsible for creating a new Teacher in the system.
 *
 * This class belongs to the application layer and orchestrates
 * the process of teacher creation by coordinating domain objects
 * and repository interactions.
 *
 * Responsibilities:
 * - Validate CPF uniqueness in the system
 * - Instantiate domain value objects (CPF and Email)
 * - Create the Teacher domain entity
 * - Persist the Teacher using the TeacherRepository
 *
 * This class does not contain persistence logic. All persistence
 * responsibilities are delegated to the repository abstraction
 * defined in the domain layer.
 *
 * @author Paulo
 */
@Component
@RequiredArgsConstructor
public class CreateTeacherUseCase {

    /**
     * Repository abstraction used to access teacher persistence.
     */
    private final TeacherRepository teacherRepository;

    /**
     * Command object used to encapsulate the data required
     * to create a new teacher.
     *
     * Using a record ensures immutability and clarity
     * when passing data to the use case.
     */
    public record CreateTeacherCommand(
        String fullName,
        String cpf,
        String email,
        String phone,
        ContractType contractType,
        Integer workloadHours,
        LocalDate hireDate
    ) {}

    /**
     * Executes the teacher creation process.
     *
     * Workflow:
     * 1. Validate that the CPF does not already exist in the system
     * 2. Instantiate domain value objects (CPF and Email)
     * 3. Create the Teacher domain entity
     * 4. Persist the entity through the repository
     *
     * @param command   Data required to create a teacher
     * @param createdBy Identifier of the user performing the action
     * @return The persisted Teacher entity
     */
    public Teacher execute(CreateTeacherCommand command, UUID createdBy) {

        // 1. Validate CPF uniqueness
        if (teacherRepository.existsByCPF(new CPF(command.cpf()))) {
            throw new RuntimeException(
                "Teacher with CPF " + command.cpf() + " already exists"
            );
        }

        // 2. Create value objects from raw input data
        CPF cpfVo = new CPF(command.cpf());
        Email emailVo = new Email(command.email());

        // 3. Instantiate the Teacher domain entity
        Teacher teacher = Teacher.create(
            command.fullName(),
            cpfVo,
            emailVo,
            command.phone(),
            command.contractType(),
            command.workloadHours(),
            command.hireDate()
        );

        // 4. Persist the teacher using the repository
        return teacherRepository.save(teacher);
    }
}
