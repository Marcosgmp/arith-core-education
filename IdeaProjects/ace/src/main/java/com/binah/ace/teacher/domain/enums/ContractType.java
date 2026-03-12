package com.binah.ace.teacher.domain.enums;

public enum ContractType {
    FULL_TIME(40), // Tempo integral (40h)
    PART_TIME(20), // Meio período (20h)
    HOURLY(0), // Por hora / aula
    SUBSTITUTE(0); // professor Substituto

    private final int DefaultWorkload;

    ContractType(int DefaultWorkload) {
        this.DefaultWorkload = DefaultWorkload;
    }

    // Retorna carga horária semanal
    public int getDefaultWorkload() {
        return DefaultWorkload;
    }

}
