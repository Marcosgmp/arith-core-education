package com.binah.ace.teacher.application.service;

import com.binah.ace.teacher.application.usecase.AssignSubjectUseCase;
import com.binah.ace.teacher.application.usecase.CreateTeacherUseCase;
import com.binah.ace.teacher.application.usecase.UpdateTeacherUseCase;
import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.teacher.domain.repository.TeacherRepository;
import com.binah.ace.teacher.domain.exception.TeacherNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Application service responsible for orchestrating Teacher use cases.
 *
 * This class belongs to the application layer and acts as a facade
 * for coordinating operations related to teachers.
 *
 * Responsibilities:
 * - Delegate creation of teachers to the corresponding use case
 * - Delegate subject assignment operations
 * - Delegate teacher updates
 * - Manage activation and inactivation of teachers
 * - Provide read operations through the repository
 *
 * This service does not contain business rules itself. Business
 * rules remain inside domain entities and use cases.
 *
 * @author Paulo
 */
@Service
@RequiredArgsConstructor
public class TeacherApplicationService {

    /**
     * Use case responsible for creating teachers.
     */
    private final CreateTeacherUseCase createTeacherUseCase;

    /**
     * Use case responsible for assigning subjects to teachers.
     */
    private final AssignSubjectUseCase assignSubjectUseCase;

    /**
     * Use case responsible for updating teacher information.
     */
    private final UpdateTeacherUseCase updateTeacherUseCase;

    /**
     * Repository used for persistence and read operations.
     */
    private final TeacherRepository teacherRepository;

    /**
     * Creates a new teacher by delegating to the corresponding use case.
     *
     * @param command   Data required to create the teacher
     * @param createdBy Identifier of the user performing the action
     * @return Persisted Teacher entity
     */
    public Teacher createTeacher(CreateTeacherUseCase.CreateTeacherCommand command, UUID createdBy) {
        return createTeacherUseCase.execute(command, createdBy);
    }

    /**
     * Assigns a subject to a teacher.
     *
     * @param teacherId Identifier of the teacher
     * @param subjectId Identifier of the subject
     * @return Updated Teacher entity
     */
    public Teacher assignSubject(UUID teacherId, UUID subjectId) {
        return assignSubjectUseCase.execute(teacherId, subjectId);
    }

    /**
     * Updates teacher information.
     *
     * @param command Command containing the updated teacher data
     * @return Updated Teacher entity
     */
    public Teacher updateTeacher(UpdateTeacherUseCase.UpdateTeacherCommand command) {
        return updateTeacherUseCase.execute(command);
    }

    /**
     * Activates a teacher account.
     *
     * @param id Teacher identifier
     * @return Updated Teacher entity
     */
    public Teacher activateTeacher(UUID id) {
        Teacher teacher = findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));

        teacher.activate();
        return teacherRepository.save(teacher);
    }

    /**
     * Inactivates a teacher account.
     *
     * @param id Teacher identifier
     * @return Updated Teacher entity
     */
    public Teacher inactivateTeacher(UUID id) {
        Teacher teacher = findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));

        teacher.inactivate();
        return teacherRepository.save(teacher);
    }

    /**
     * Finds a teacher by its identifier.
     *
     * @param id Teacher identifier
     * @return Optional containing the teacher if found
     */
    public Optional<Teacher> findById(UUID id) {
        return teacherRepository.findById(id);
    }

    /**
     * Retrieves all active teachers.
     *
     * @return List of active teachers
     */
    public List<Teacher> findActiveTeachers() {
        return teacherRepository.findActiveTeachers();
    }

    /**
     * Searches teachers by name.
     *
     * @param name Name or partial name
     * @return List of matching teachers
     */
    public List<Teacher> searchByName(String name) {
        return teacherRepository.searchByName(name);
    }

    /**
     * Finds teachers assigned to a specific subject.
     *
     * @param subjectId Subject identifier
     * @return List of teachers associated with the subject
     */
    public List<Teacher> findBySubjectId(UUID subjectId) {
        return teacherRepository.findBySubjectId(subjectId);
    }
}
