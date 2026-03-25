package com.binah.ace.student.domain.port;

import com.binah.ace.shared.valueobject.Email;

/**
 * Output port for the notification service.
 *
 * Defines the contract that the Student module expects
 * from an external notification service.
 *
 * Implementation is located in Infrastructure (adapter).
 *
 * @author Marcos Gustavo
 */
public interface NotificationPort {

    /**
     * Sends a welcome email to a new student.
     *
     * @param studentEmail Student's email
     * @param studentName Student's name
     * @param enrollmentNumber Enrollment number
     */
    void sendWelcomeEmail(Email studentEmail, String studentName, String enrollmentNumber);

    /**
     * Sends a notification when a grade is posted.
     *
     * @param studentEmail Student's email
     * @param studentName Student's name
     * @param subjectName Subject name
     * @param score Obtained score
     */
    void sendGradeNotification(
            Email studentEmail,
            String studentName,
            String subjectName,
            double score
    );

    /**
     * Sends a low attendance warning notification.
     *
     * @param studentEmail Student's email
     * @param guardianEmail Guardian's email
     * @param studentName Student's name
     * @param attendancePercentage Attendance percentage
     */
    void sendLowAttendanceWarning(
            Email studentEmail,
            Email guardianEmail,
            String studentName,
            double attendancePercentage
    );

    /**
     * Sends the report card via email.
     *
     * @param studentEmail Student's email
     * @param guardianEmail Guardian's email
     * @param reportCardPdfUrl URL of the report card PDF
     */
    void sendReportCard(
            Email studentEmail,
            Email guardianEmail,
            String reportCardPdfUrl
    );
}