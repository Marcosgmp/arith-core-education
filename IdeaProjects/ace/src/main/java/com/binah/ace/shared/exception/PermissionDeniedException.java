package com.binah.ace.shared.exception;

/**
 * Exceção lançada quando um usuário não tem permissão para executar uma ação.
 *
 * Usada no sistema de autorização (JWT, Roles, Permissions).
 *
 * Exemplos de uso:
 * - Aluno tentando criar outro aluno
 * - Professor tentando deletar turma
 * - Usuário sem role adequada
 *
 * @author Marcos Gustavo
 */
public class PermissionDeniedException extends BusinessException {

    /**
     * Construtor com mensagem genérica.
     *
     * @param message Mensagem de erro
     */
    public PermissionDeniedException(String message) {
        super("PERMISSION_DENIED", message);
    }

    /**
     * Construtor com ação e recurso.
     *
     * Gera mensagem automática: "Permission denied to DELETE on Student"
     *
     * @param action Ação tentada (CREATE, UPDATE, DELETE, VIEW)
     * @param resource Recurso afetado (Student, Teacher, etc)
     */
    public PermissionDeniedException(String action, String resource) {
        super(
                "PERMISSION_DENIED",
                String.format("Permission denied to %s on %s", action, resource)
        );
    }

    /**
     * Construtor sem parâmetros.
     *
     * Mensagem padrão: "You don't have permission to perform this action"
     */
    public PermissionDeniedException() {
        super(
                "PERMISSION_DENIED",
                "You don't have permission to perform this action"
        );
    }
}