package com.binah.ace.student.application.usecase;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.exception.InvalidEnrollmentException;
import com.binah.ace.student.domain.port.AuditPort;
import com.binah.ace.student.domain.port.NotificationPort;
import com.binah.ace.student.domain.repository.StudentRepository;
import com.binah.ace.student.domain.valueobject.Enrollment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Use case: Create a new student.
 *
 * Orchestrates:
 * 1. Duplicate validation (CPF)
 * 2. Enrollment number generation
 * 3. Student creation
 * 4. Persistence
 * 5. Notification
 * 6. Auditing
 *
 * @author Marcos Gustavo
 */
@Service
public class CreateStudentUseCase {

    private final StudentRepository studentRepository;
    private final NotificationPort notificationPort;
    private final AuditPort auditPort;

    public CreateStudentUseCase(
            StudentRepository studentRepository,
            NotificationPort notificationPort,
            AuditPort auditPort
    ) {
        this.studentRepository = studentRepository;
        this.notificationPort = notificationPort;
        this.auditPort = auditPort;
    }

    /**
     * Executes student creation.
     *
     * @param command Student data
     * @param createdBy ID of the user who created the student
     * @return Created student
     */
    @Transactional
    public Student execute(CreateStudentCommand command, UUID createdBy) {
        // 1. Validate if CPF already exists
        CPF cpf = new CPF(command.cpf());
        if (studentRepository.existsByCPF(cpf)) {
            throw InvalidEnrollmentException.duplicateCPF(command.cpf());
        }

        // 2. Generate enrollment number
        Enrollment enrollment = generateEnrollmentNumber();

        // 3. Create email
        Email email = new Email(command.email());
        Email guardianEmail = new Email(command.guardianEmail());

        // 4. Create student
        Student student = new Student(
                UUID.randomUUID(),
                command.fullName(),
                cpf,
                email,
                command.birthDate(),
                command.phone(),
                command.address(),
                command.guardianName(),
                command.guardianPhone(),
                guardianEmail
        );

        // 5. Enroll the student
        student.enroll(enrollment);

        // 6. Persist
        student = studentRepository.save(student);

        // 7. Send welcome notification
        notificationPort.sendWelcomeEmail(
                email,
                student.getFullName(),
                enrollment.enrollmentNumber()
        );

        // 8. Record audit log
        auditPort.recordStudentCreated(
                student.getId(),
                createdBy,
                enrollment.enrollmentNumber()
        );

        return student;
    }

    /**
     * Generates a unique enrollment number.
     *
     * Format: YYYYMMDD-XXX
     */
    private Enrollment generateEnrollmentNumber() {
        int sequenceNumber = 1;
        Enrollment enrollment;

        // Attempts to generate a unique number
        do {
            enrollment = Enrollment.generate(sequenceNumber);
            sequenceNumber++;
        } while (studentRepository.existsByEnrollmentNumber(enrollment));

        return enrollment;
    }

    /**
     * Command to create a student.
     */
    public record CreateStudentCommand(
            String fullName,
            String cpf,
            String email,
            LocalDate birthDate,
            String phone,
            String address,
            String guardianName,
            String guardianPhone,
            String guardianEmail
    ) {}
}