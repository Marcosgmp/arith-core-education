package com.binah.ace.shared.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utilitários para paginação em consultas GraphQL e JPA.
 *
 * Fornece métodos para criar objetos Pageable de forma padronizada,
 * com validações e valores padrão.
 *
 * Limites:
 * - Tamanho padrão: 20 itens por página
 * - Tamanho máximo: 100 itens por página
 * - Página inicial: 0 (zero-indexed)
 *
 * @author Marcos Gustavo
 */
public class PaginationUtils {

    /** Tamanho padrão de página quando não especificado */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /** Tamanho máximo permitido para evitar sobrecarga */
    private static final int MAX_PAGE_SIZE = 100;

    /** Página inicial padrão (zero-indexed) */
    private static final int DEFAULT_PAGE = 0;

    /**
     * Construtor privado para classe utilitária.
     */
    private PaginationUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Cria um PageRequest simples com validações.
     *
     * Regras:
     * - Se page for null ou negativo, usa 0
     * - Se size for null, negativo ou zero, usa DEFAULT_PAGE_SIZE (20)
     * - Se size for maior que MAX_PAGE_SIZE (100), usa MAX_PAGE_SIZE
     *
     * @param page Número da página (zero-indexed)
     * @param size Quantidade de itens por página
     * @return PageRequest validado
     */
    public static Pageable createPageRequest(Integer page, Integer size) {
        int validPage = validatePage(page);
        int validSize = validateSize(size);
        return PageRequest.of(validPage, validSize);
    }

    /**
     * Cria um PageRequest com ordenação.
     *
     * @param page Número da página (zero-indexed)
     * @param size Quantidade de itens por página
     * @param sort Ordenação a ser aplicada
     * @return PageRequest validado com ordenação
     */
    public static Pageable createPageRequest(
            Integer page,
            Integer size,
            Sort sort
    ) {
        int validPage = validatePage(page);
        int validSize = validateSize(size);

        if (sort == null || sort.isUnsorted()) {
            return PageRequest.of(validPage, validSize);
        }

        return PageRequest.of(validPage, validSize, sort);
    }

    /**
     * Cria um PageRequest com ordenação por campo e direção.
     *
     * @param page Número da página (zero-indexed)
     * @param size Quantidade de itens por página
     * @param sortBy Campo para ordenação (ex: "name", "createdAt")
     * @param direction Direção da ordenação ("ASC" ou "DESC")
     * @return PageRequest validado com ordenação
     */
    public static Pageable createPageRequest(
            Integer page,
            Integer size,
            String sortBy,
            String direction
    ) {
        Sort sort = createSort(sortBy, direction);
        return createPageRequest(page, size, sort);
    }

    /**
     * Cria um PageRequest com ordenação por múltiplos campos.
     *
     * @param page Número da página
     * @param size Quantidade de itens por página
     * @param sortBy Array de campos para ordenação
     * @param direction Direção da ordenação
     * @return PageRequest validado
     */
    public static Pageable createPageRequest(
            Integer page,
            Integer size,
            String[] sortBy,
            String direction
    ) {
        if (sortBy == null || sortBy.length == 0) {
            return createPageRequest(page, size);
        }

        Sort.Direction sortDirection = parseSortDirection(direction);
        Sort sort = Sort.by(sortDirection, sortBy);

        return createPageRequest(page, size, sort);
    }

    /**
     * Cria um objeto Sort a partir de campo e direção.
     *
     * @param sortBy Campo para ordenação
     * @param direction Direção ("ASC" ou "DESC")
     * @return Sort configurado ou unsorted se sortBy for null/vazio
     */
    public static Sort createSort(String sortBy, String direction) {
        if (sortBy == null || sortBy.isBlank()) {
            return Sort.unsorted();
        }

        Sort.Direction sortDirection = parseSortDirection(direction);
        return Sort.by(sortDirection, sortBy);
    }

    /**
     * Valida e normaliza o número da página.
     *
     * @param page Número da página fornecido
     * @return Número de página válido (>= 0)
     */
    private static int validatePage(Integer page) {
        if (page == null || page < 0) {
            return DEFAULT_PAGE;
        }
        return page;
    }

    /**
     * Valida e normaliza o tamanho da página.
     *
     * Regras:
     * - Se null, negativo ou zero → DEFAULT_PAGE_SIZE (20)
     * - Se maior que MAX_PAGE_SIZE (100) → MAX_PAGE_SIZE
     *
     * @param size Tamanho fornecido
     * @return Tamanho válido (1 a MAX_PAGE_SIZE)
     */
    private static int validateSize(Integer size) {
        if (size == null || size <= 0) {
            return DEFAULT_PAGE_SIZE;
        }

        if (size > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        }

        return size;
    }

    /**
     * Converte string de direção para Sort.Direction.
     *
     * @param direction String "ASC" ou "DESC" (case-insensitive)
     * @return Sort.Direction correspondente (padrão: ASC)
     */
    private static Sort.Direction parseSortDirection(String direction) {
        if (direction == null || direction.isBlank()) {
            return Sort.Direction.ASC;
        }

        return "DESC".equalsIgnoreCase(direction.trim())
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
    }

    /**
     * Retorna o tamanho padrão de página.
     *
     * @return DEFAULT_PAGE_SIZE (20)
     */
    public static int getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    /**
     * Retorna o tamanho máximo permitido de página.
     *
     * @return MAX_PAGE_SIZE (100)
     */
    public static int getMaxPageSize() {
        return MAX_PAGE_SIZE;
    }
}