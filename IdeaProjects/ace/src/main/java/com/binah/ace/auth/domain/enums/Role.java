package com.binah.ace.auth.domain.enums;


/**
 * User roles in the ACE system.
 *
 * Defines access levels and permissions.
 * Each user has exactly one primary role.
 *
 * @author Marcos Gustavo
 */
public enum Role {

    /**
     * System administrator.
     *
     * Has full access to all system functionalities.
     */
    ADMIN ("Administrator"),
    /**
     * Teacher.
     *
     * Can manage classes, submit grades, and record attendance.
     */
    TEACHER("Teacher"),

    /**
     * Student.
     *
     * Can view their grades, attendance, and report card.
     */
    STUDENT("Student"),

    /**
     * Student's guardian.
     *
     * Can view data related to the associated student.
     */
    GUARDIAN("Guardian"),


    /**
     * Administrative staff (secretary).
     *
     * Can create students and teachers, manage enrollments,
     * and generate reports.
     */
    STAFF("Staff");
    ;

    private final String displayName;

    /**
     * Private constructor
     * @param displayName
     */
    Role(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Checks whether the role is ADMIN or STAFF.
     * @return {@code true} if the role is ADMIN or STAFF
     */
    public boolean isAdministrative(){
        return this == ADMIN || this == STAFF;
    }

    /**
     * Checks whether the role is Teacher
     * @return {@code true} if the role is teacher
     */
    public boolean isEducator(){
        return this == TEACHER;
    }

    /**
     * Checks whether the role is Student.
     * @return {@code true} is the role is student
     */
    public boolean isStudent(){
        return this == STUDENT;
    }



}
