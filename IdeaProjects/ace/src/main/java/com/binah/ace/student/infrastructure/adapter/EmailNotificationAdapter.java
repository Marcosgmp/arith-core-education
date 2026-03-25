package com.binah.ace.student.infrastructure.adapter;

import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.student.domain.port.NotificationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Módulo incompleto
 *
 * Adapter que implementa NotificationPort.
 *
 * Por enquanto apenas loga as notificações.
 * Futuramente: integrar com serviço de email real (SMTP, SendGrid, etc).
 *
 * @author Marcos Gustavo
 */
@Component
public class EmailNotificationAdapter implements NotificationPort {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationAdapter.class);

    @Override
    public void sendWelcomeEmail(Email studentEmail, String studentName, String enrollmentNumber) {
        log.info("📧 Sending welcome email to {} ({}). Enrollment: {}",
                studentName, studentEmail.value(), enrollmentNumber);

        // TODO: Implementar envio real de email
        // emailService.send(studentEmail, "Welcome to ACE", template);
    }

    @Override
    public void sendGradeNotification(
            Email studentEmail,
            String studentName,
            String subjectName,
            double score
    ) {
        log.info("📧 Sending grade notification to {} ({}). Subject: {}, Score: {}",
                studentName, studentEmail.value(), subjectName, score);

        // TODO: Implementar envio real
    }

    @Override
    public void sendLowAttendanceWarning(
            Email studentEmail,
            Email guardianEmail,
            String studentName,
            double attendancePercentage
    ) {
        log.warn("⚠️ Sending low attendance warning for {} ({}) - Attendance: {}%",
                studentName, studentEmail.value(), attendancePercentage);

        // TODO: Implementar envio real para aluno e responsável
    }

    @Override
    public void sendReportCard(
            Email studentEmail,
            Email guardianEmail,
            String reportCardPdfUrl
    ) {
        log.info("📧 Sending report card to {} and {}",
                studentEmail.value(), guardianEmail.value());

        // TODO: Implementar envio real com anexo PDF
    }
}