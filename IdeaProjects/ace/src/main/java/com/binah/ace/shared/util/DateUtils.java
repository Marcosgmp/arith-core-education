package com.binah.ace.shared.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utilitários para manipulação de datas no sistema ACE.
 *
 * Fornece métodos auxiliares para:
 * - Formatação de datas
 * - Cálculo de idade
 * - Validação de períodos
 *
 * @author Marcos Gustavo
 */
public class DateUtils {

    // Formatadores padrão
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final DateTimeFormatter ISO_DATE_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Construtor privado para classe utilitária.
     */
    private DateUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Formata uma data para string no padrão brasileiro (dd/MM/yyyy).
     *
     * @param date Data a ser formatada
     * @return String formatada ou null se date for null
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    /**
     * Formata uma data/hora para string no padrão brasileiro.
     *
     * @param dateTime Data/hora a ser formatada
     * @return String formatada ou null se dateTime for null
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : null;
    }

    /**
     * Formata uma data para string no padrão ISO (yyyy-MM-dd).
     *
     * @param date Data a ser formatada
     * @return String no formato ISO ou null se date for null
     */
    public static String formatDateISO(LocalDate date) {
        return date != null ? date.format(ISO_DATE_FORMATTER) : null;
    }

    /**
     * Converte string para LocalDate no padrão brasileiro (dd/MM/yyyy).
     *
     * @param dateStr String no formato dd/MM/yyyy
     * @return LocalDate correspondente
     * @throws java.time.format.DateTimeParseException se formato inválido
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * Converte string para LocalDate no padrão ISO (yyyy-MM-dd).
     *
     * @param dateStr String no formato yyyy-MM-dd
     * @return LocalDate correspondente
     * @throws java.time.format.DateTimeParseException se formato inválido
     */
    public static LocalDate parseDateISO(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        return LocalDate.parse(dateStr, ISO_DATE_FORMATTER);
    }

    /**
     * Calcula a idade em anos completos a partir da data de nascimento.
     *
     * Exemplo: nascido em 15/03/2010, hoje é 16/02/2026 → idade = 15
     *
     * @param birthDate Data de nascimento
     * @return Idade em anos completos
     */
    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }

    /**
     * Verifica se uma data está entre duas outras datas (inclusive).
     *
     * @param date Data a verificar
     * @param start Data inicial do período
     * @param end Data final do período
     * @return true se date está entre start e end (inclusive)
     */
    public static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null || start == null || end == null) {
            return false;
        }
        return !date.isBefore(start) && !date.isAfter(end);
    }

    /**
     * Verifica se uma data está no passado.
     *
     * @param date Data a verificar
     * @return true se a data é anterior a hoje
     */
    public static boolean isPast(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }

    /**
     * Verifica se uma data está no futuro.
     *
     * @param date Data a verificar
     * @return true se a data é posterior a hoje
     */
    public static boolean isFuture(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }

    /**
     * Retorna a data atual.
     *
     * @return LocalDate de hoje
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * Retorna a data/hora atual.
     *
     * @return LocalDateTime de agora
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Calcula a diferença em dias entre duas datas.
     *
     * @param start Data inicial
     * @param end Data final
     * @return Número de dias entre as datas
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        return ChronoUnit.DAYS.between(start, end);
    }
}
