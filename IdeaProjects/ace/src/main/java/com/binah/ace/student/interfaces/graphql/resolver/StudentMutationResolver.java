package com.binah.ace.student.interfaces.graphql.resolver;

import com.binah.ace.student.application.service.StudentApplicationService;
import com.binah.ace.student.application.usecase.CreateStudentUseCase;
import com.binah.ace.student.application.usecase.PostGradeUseCase;
import com.binah.ace.student.domain.entity.Grade;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.interfaces.graphql.dto.GradeDTO;
import com.binah.ace.student.interfaces.graphql.dto.StudentDTO;
import com.binah.ace.student.interfaces.graphql.input.CreateStudentInput;
import com.binah.ace.student.interfaces.graphql.input.PostGradeInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.UUID;

/**
 * GraphQL resolver for mutations of the Student module.
 *
 * Mutations are WRITE operations (create/modify data).
 *
 * @author Marcos Gustavo
 */
@Controller
public class StudentMutationResolver {

    private final StudentApplicationService applicationService;

    public StudentMutationResolver(StudentApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Mutation: createStudent
     *
     * Creates a new student.
     *
     * GraphQL:
     * mutation {
     *   createStudent(input: {
     *     fullName: "João Silva"
     *     cpf: "12345678901"
     *     email: "joao@email.com"
     *     birthDate: "2010-05-15"
     *     phone: "11999999999"
     *     address: "Rua X, 123"
     *     guardianName: "Maria Silva"
     *     guardianPhone: "11988888888"
     *     guardianEmail: "maria@email.com"
     *   }) {
     *     id
     *     fullName
     *     enrollmentNumber
     *   }
     * }
     */
    @MutationMapping
    public StudentDTO createStudent(
            @Argument CreateStudentInput input,
            Authentication authentication
    ) {
        // Validate input
        input.validate();

        // Extract ID of the authenticated user
        UUID createdBy = (UUID) authentication.getPrincipal();

        // Create command
        CreateStudentUseCase.CreateStudentCommand command =
                new CreateStudentUseCase.CreateStudentCommand(
                        input.fullName(),
                        input.cpf(),
                        input.email(),
                        input.birthDate(),
                        input.phone(),
                        input.address(),
                        input.guardianName(),
                        input.guardianPhone(),
                        input.guardianEmail()
                );

        // Execute use case
        Student student = applicationService.createStudent(command, createdBy);

        return StudentDTO.from(student);
    }

    /**
     * Mutation: postGrade
     *
     * Posts a student's grade.
     *
     * GraphQL:
     * mutation {
     *   postGrade(input: {
     *     studentId: "uuid"
     *     classroomSubjectId: "uuid"
     *     assessmentType: EXAM
     *     score: 8.5
     *     weight: 1.0
     *     description: "Prova Bimestral"
     *     assessmentDate: "2026-03-01"
     *     year: 2026
     *     semester: 1
     *     bimester: 1
     *   }) {
     *     id
     *     score
     *     isPassing
     *   }
     * }
     */
    @MutationMapping
    public GradeDTO postGrade(
            @Argument PostGradeInput input,
            Authentication authentication
    ) {
        // Validate input
        input.validate();

        // Extract ID of the authenticated teacher
        UUID postedBy = (UUID) authentication.getPrincipal();

        // Create command
        PostGradeUseCase.PostGradeCommand command =
                new PostGradeUseCase.PostGradeCommand(
                        input.studentId(),
                        input.classroomSubjectId(),
                        input.assessmentType(),
                        input.score(),
                        input.weight(),
                        input.description(),
                        input.assessmentDate(),
                        input.year(),
                        input.semester(),
                        input.bimester()
                );

        // Execute use case
        Grade grade = applicationService.postGrade(command, postedBy);

        return GradeDTO.from(grade);
    }
}
