package com.binah.ace.student.domain.entity;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.student.domain.enums.StudentStatus;
import com.binah.ace.student.domain.valueobject.Enrollment;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity representing a Student.
 *
 * WITHOUT JPA annotations - this is the PURE domain representation.
 *
 * Responsible for:
 * - Managing student personal data
 * - Controlling academic status
 * - Validating enrollment rules
 * - Controlling eligibility for academic actions
 *
 * @author Marcos Gustavo
 */
@Getter
public class Student {

    private final UUID id;
    private String fullName;
    private CPF cpf;
    private Email email;
    private LocalDate birthDate;
    private String phone;
    private String address;

    // Academic data
    private Enrollment enrollment;
    private StudentStatus status;
    private LocalDate enrollmentDate;
    private String guardianName;
    private String guardianPhone;
    private Email guardianEmail;

    // Audit fields
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor to create a new student.
     */
    public Student(
            UUID id,
            String fullName,
            CPF cpf,
            Email email,
            LocalDate birthDate,
            String phone,
            String address,
            String guardianName,
            String guardianPhone,
            Email guardianEmail
    ) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.guardianEmail = guardianEmail;
        this.status = StudentStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Full constructor (for database reconstruction).
     */
    public Student(
            UUID id,
            String fullName,
            CPF cpf,
            Email email,
            LocalDate birthDate,
            String phone,
            String address,
            Enrollment enrollment,
            StudentStatus status,
            LocalDate enrollmentDate,
            String guardianName,
            String guardianPhone,
            Email guardianEmail,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.enrollment = enrollment;
        this.status = status;
        this.enrollmentDate = enrollmentDate;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.guardianEmail = guardianEmail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // BUSINESS RULES

    /**
     * Enrolls the student in the system.
     *
     * @param enrollment Generated enrollment
     * @throws IllegalStateException if the student is already enrolled
     */
    public void enroll(Enrollment enrollment) {
        if (this.enrollment != null) {
            throw new IllegalStateException("Student is already enrolled");
        }

        this.enrollment = enrollment;
        this.enrollmentDate = enrollment.enrollmentDate();
        this.status = StudentStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Graduates the student (course completed).
     *
     * @throws IllegalStateException if the student is not active
     */
    public void graduate() {
        if (this.status != StudentStatus.ACTIVE) {
            throw new IllegalStateException("Only active students can graduate");
        }

        this.status = StudentStatus.GRADUATED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Inactivates the student (leave of absence, temporary withdrawal).
     */
    public void inactivate() {
        if (this.status == StudentStatus.GRADUATED) {
            throw new IllegalStateException("Cannot inactivate graduated student");
        }

        this.status = StudentStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Reactivates the student.
     *
     * @throws IllegalStateException if already active or graduated
     */
    public void reactivate() {
        if (this.status == StudentStatus.ACTIVE) {
            throw new IllegalStateException("Student is already active");
        }

        if (this.status == StudentStatus.GRADUATED) {
            throw new IllegalStateException("Cannot reactivate graduated student");
        }

        this.status = StudentStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Transfers the student to another institution.
     */
    public void transfer() {
        this.status = StudentStatus.TRANSFERRED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Expels the student.
     */
    public void expel() {
        this.status = StudentStatus.EXPELLED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Checks if the student can enroll in classes.
     */
    public boolean canEnroll() {
        return this.status == StudentStatus.ACTIVE && this.enrollment != null;
    }

    /**
     * Checks if the student is active.
     */
    public boolean isActive() {
        return this.status == StudentStatus.ACTIVE;
    }

    /**
     * Calculates the student's age.
     */
    public int getAge() {
        return LocalDate.now().getYear() - this.birthDate.getYear();
    }

    /**
     * Checks if the student is a minor (< 18 years old).
     */
    public boolean isMinor() {
        return getAge() < 18;
    }

    /**
     * Updates personal information.
     */
    public void updatePersonalInfo(
            String fullName,
            Email email,
            String phone,
            String address
    ) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates guardian information.
     */
    public void updateGuardianInfo(
            String guardianName,
            String guardianPhone,
            Email guardianEmail
    ) {
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.guardianEmail = guardianEmail;
        this.updatedAt = LocalDateTime.now();
    }
}