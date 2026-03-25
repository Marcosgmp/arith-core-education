package com.binah.ace.student.interfaces.graphql.resolver;

import com.binah.ace.student.application.service.StudentApplicationService;
import com.binah.ace.student.application.usecase.ViewReportCardUseCase;
import com.binah.ace.student.domain.entity.Student;
import com.binah.ace.student.domain.exception.StudentNotFoundException;
import com.binah.ace.student.domain.repository.StudentRepository;
import com.binah.ace.student.domain.valueobject.AcademicPeriod;
import com.binah.ace.student.interfaces.graphql.dto.ReportCardDTO;
import com.binah.ace.student.interfaces.graphql.dto.StudentDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * GraphQL resolver for queries of the Student module.
 *
 * Queries are READ operations (do not modify data).
 *
 * @author Marcos Gustavo
 */
@Controller
public class StudentQueryResolver {

    private final StudentRepository studentRepository;
    private final StudentApplicationService applicationService;

    public StudentQueryResolver(
            StudentRepository studentRepository,
            StudentApplicationService applicationService
    ) {
        this.studentRepository = studentRepository;
        this.applicationService = applicationService;
    }

    /**
     * Query: student
     *
     * Fetch a student by ID.
     *
     * GraphQL:
     * query {
     *   student(id: "uuid") {
     *     id
     *     fullName
     *     email
     *   }
     * }
     */
    @QueryMapping
    public StudentDTO student(@Argument UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        return StudentDTO.from(student);
    }

    /**
     * Query: students
     *
     * Lists all active students.
     *
     * GraphQL:
     * query {
     *   students {
     *     id
     *     fullName
     *   }
     * }
     */
    @QueryMapping
    public List<StudentDTO> students() {
        return studentRepository.findActiveStudents()
                .stream()
                .map(StudentDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * Query: searchStudents
     *
     * Searches students by name.
     *
     * GraphQL:
     * query {
     *   searchStudents(name: "João") {
     *     id
     *     fullName
     *   }
     * }
     */
    @QueryMapping
    public List<StudentDTO> searchStudents(@Argument String name) {
        return studentRepository.searchByName(name)
                .stream()
                .map(StudentDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * Query: reportCard
     *
     * View the student's report card.
     *
     * GraphQL:
     * query {
     *   reportCard(studentId: "uuid", year: 2026, semester: 1) {
     *     studentName
     *     gpa
     *     isApproved
     *   }
     * }
     */
    @QueryMapping
    public ReportCardDTO reportCard(
            @Argument UUID studentId,
            @Argument Integer year,
            @Argument Integer semester,
            @Argument Integer bimester
    ) {
        AcademicPeriod period = bimester != null
                ? AcademicPeriod.of(year, semester, bimester)
                : AcademicPeriod.of(year, semester);

        ViewReportCardUseCase.ReportCard reportCard =
                applicationService.viewReportCard(studentId, period);

        return ReportCardDTO.from(reportCard);
    }
}