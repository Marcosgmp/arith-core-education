package com.binah.ace.shared.events;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe base para todos os Domain Events no sistema ACE.
 *
 * Exemplos de eventos:
 * - StudentCreatedEvent
 * - GradePostedEvent
 * - EnrollmentCompletedEvent
 * - UserLoggedInEvent
 *
 * Uso:
 * 1. Criar evento específico estendendo esta classe
 * 2. Publicar usando ApplicationEventPublisher
 * 3. Criar listener com @EventListener
 *
 *
 * @author Marcos Gustavo
 */
@Getter
public abstract class DomainEvent {
    private final UUID eventId;
    private final LocalDateTime occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format(
                "%s[eventId=%s, occurredOn=%s]",
                this.getClass().getSimpleName(),
                eventId,
                occurredOn
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DomainEvent that = (DomainEvent) obj;
        return eventId.equals(that.eventId);
    }

    @Override
    public int hashCode() {
        return eventId.hashCode();
    }
}
