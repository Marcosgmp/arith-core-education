package com.binah.ace.student.domain.enums;

/**
 * Academic assessment types.
 *
 * Defines the different types of evaluative activities
 * that can generate grades.
 *
 * @author Marcos Gustavo
 */
public enum AssessmentType {

    /**
     * Formal exam (written or oral).
     */
    EXAM("Exam"),

    /**
     * Short test (quiz).
     */
    QUIZ("Quiz"),

    /**
     * Project (individual or group).
     */
    PROJECT("Project"),

    /**
     * Homework assignment.
     */
    ASSIGNMENT("Assignment"),

    /**
     * Class participation grade.
     */
    PARTICIPATION("Participation"),

    /**
     * Oral presentation.
     */
    PRESENTATION("Presentation"),

    /**
     * Laboratory assignment.
     */
    LAB_WORK("Lab Work");

    private final String displayName;

    AssessmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if this is a formal assessment (exam).
     *
     * @return true if EXAM
     */
    public boolean isFormalExam() {
        return this == EXAM;
    }
}