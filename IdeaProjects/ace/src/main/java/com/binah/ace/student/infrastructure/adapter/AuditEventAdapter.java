package com.binah.ace.student.infrastructure.adapter;

import com.binah.ace.student.domain.port.AuditPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Módulo incompleto
 * Adapter que implementa AuditPort.
 *
 * Por enquanto apenas loga eventos de auditoria.
 * Futuramente: integrar com módulo Audit (MongoDB).
 *
 * @author Marcos Gustavo
 */
@Component
public class AuditEventAdapter implements AuditPort {

    private static final Logger log = LoggerFactory.getLogger(AuditEventAdapter.class);

    @Override
    public void recordStudentCreated(UUID studentId, UUID createdBy, String enrollmentNumber) {
        log.info("📝 AUDIT: Student created - ID: {}, Enrollment: {}, By: {}",
                studentId, enrollmentNumber, createdBy);

        // TODO: Enviar para módulo Audit (MongoDB)
        // auditService.record(new StudentCreatedEvent(...));
    }

    @Override
    public void recordGradePosted(UUID gradeId, UUID studentId, UUID postedBy, double score) {
        log.info("📝 AUDIT: Grade posted - ID: {}, Student: {}, Score: {}, By: {}",
                gradeId, studentId, score, postedBy);

        // TODO: Enviar para módulo Audit
    }

    @Override
    public void recordGradeUpdated(
            UUID gradeId,
            double oldScore,
            double newScore,
            UUID updatedBy
    ) {
        log.info("📝 AUDIT: Grade updated - ID: {}, Old: {}, New: {}, By: {}",
                gradeId, oldScore, newScore, updatedBy);

        // TODO: Enviar para módulo Audit
    }

    @Override
    public void recordStatusChanged(
            UUID studentId,
            String oldStatus,
            String newStatus,
            UUID changedBy,
            String reason
    ) {
        log.info("📝 AUDIT: Student status changed - ID: {}, {} → {}, Reason: {}, By: {}",
                studentId, oldStatus, newStatus, reason, changedBy);

        // TODO: Enviar para módulo Audit
    }

    @Override
    public void recordGraduation(UUID studentId, double finalGPA) {
        log.info("🎓 AUDIT: Student graduated - ID: {}, Final GPA: {}",
                studentId, finalGPA);

        // TODO: Enviar para módulo Audit
    }
}