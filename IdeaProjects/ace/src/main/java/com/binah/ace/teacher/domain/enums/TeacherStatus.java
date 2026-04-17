package com.binah.ace.teacher.domain.enums;

public enum TeacherStatus {
    ACTIVE, // ativo
    INACTIVE, // inativo / afastado
    ON_LEAVE, // licença
    TERMINATED; // desligado

    // Verificação se professor está ativo
    public boolean isActive() {
        return this == ACTIVE;
    }

    // Verificação se professor pode ensinar
    public boolean canTeach() {
        return this == ACTIVE;
    }
}