package com.binah.ace.student.application.usecase;

import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.enums.AssessmentType;
import com.binah.ace.student.domain.exception.InvalidGradeException;
import com.binah.ace.student.domain.exception.StudentNotFoundException;
import com.binah.ace.student.domain.port.AuditPort;
import com.binah.ace.student.domain.port.NotificationPort;
import com.binah.ace.student.domain.repository.GradeRepository;
import com.binah.ace.student.domain.repository.StudentRepository;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Use case: Post a student's grade.
 *
 * Orchestrates:
 * 1. Student validation
 * 2. Grade validation
 * 3. Grade record creation
 * 4. Persistence
 * 5. Notification
 * 6. Auditing
 *
 * @author Marcos Gustavo
 */
@Service
public class PostGradeUseCase {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final NotificationPort notificationPort;
    private final AuditPort auditPort;

    public PostGradeUseCase(
            StudentRepository studentRepository,
            GradeRepository gradeRepository,
            NotificationPort notificationPort,
            AuditPort auditPort
    ) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.notificationPort = notificationPort;
        this.auditPort = auditPort;
    }

    /**
     * Executes grade posting.
     *
     * @param command Grade data
     * @param postedBy ID of the teacher who posted the grade
     * @return Created grade
     */
    @Transactional
    public Grade execute(PostGradeCommand command, UUID postedBy) {
        // 1. Validate if the student exists
        Student student = studentRepository.findById(command.studentId())
                .orElseThrow(() -> new StudentNotFoundException(command.studentId()));

        // 2. Validate if the student is active
        if (!student.isActive()) {
            throw InvalidGradeException.studentNotEnrolled();
        }

        // 3. Validate grade
        validateScore(command.score());

        // 4. Create academic period
        AcademicPeriod period = AcademicPeriod.of(
                command.year(),
                command.semester(),
                command.bimester()
        );

        // 5. Create grade
        Grade grade = new Grade(
                UUID.randomUUID(),
                student.getId(),
                command.classroomSubjectId(),
                command.assessmentType(),
                period,
                command.score(),
                command.weight(),
                command.description(),
                command.assessmentDate(),
                postedBy
        );

        // 6. Persist in the database
        grade = gradeRepository.save(grade);

        // 7. Notify the student about the new grade
        notificationPort.sendGradeNotification(
                student.getEmail(),
                student.getFullName(),
                "Subject Name", // TODO: buscar nome da disciplina do módulo classroom
                grade.getScore().doubleValue()
        );

        // 8. Record audit log
        auditPort.recordGradePosted(
                grade.getId(),
                student.getId(),
                postedBy,
                grade.getScore().doubleValue()
        );

        return grade;
    }

    /**
     * Validates whether the grade is within the correct range.
     */
    private void validateScore(BigDecimal score) {
        if (score.compareTo(BigDecimal.ZERO) < 0 ||
                score.compareTo(BigDecimal.TEN) > 0) {
            throw InvalidGradeException.scoreOutOfRange(score.doubleValue());
        }
    }

    /**
     * Command to post a grade.
     */
    public record PostGradeCommand(
            UUID studentId,
            UUID classroomSubjectId,
            AssessmentType assessmentType,
            BigDecimal score,
            BigDecimal weight,
            String description,
            LocalDate assessmentDate,
            int year,
            int semester,
            Integer bimester
    ) {}
}