package com.binah.ace.shared.exception;

/**
 * Essa exceção sera lançada quando uma entidade não for encontrada no sistema.
 *
 * Usada em operações de busca (findById, findByEmail, etc) quando
 * o registro não existe no banco de dados.
 *
 * Exemplos de uso:
 * - Student não encontrado
 * - Teacher não encontrado
 * - Classroom não encontrada
 *
 * @author Marcos Gustavo
 */
public class EntityNotFoundException extends BusinessException {

    /**
     * Construtor com nome da entidade e ID.
     *
     * Gera mensagem automática: "Student with id 123 not found"
     *
     * @param entityName Nome da entidade (Student, Teacher, etc)
     * @param id Identificador da entidade
     */
    public EntityNotFoundException(String entityName, Object id) {
        super(
                "ENTITY_NOT_FOUND",
                String.format("%s with id %s not found", entityName, id)
        );
    }

    /**
     * Construtor apenas com nome da entidade.
     *
     * Gera mensagem automática: "Student not found"
     *
     * @param entityName Nome da entidade
     */
    public EntityNotFoundException(String entityName) {
        super(
                "ENTITY_NOT_FOUND",
                String.format("%s not found", entityName)
        );
    }

    /**
     * Construtor com mensagem customizada.
     *
     * Use quando precisar de uma mensagem específica.
     *
     * @param message Mensagem customizada
     */
    public EntityNotFoundException(String message, boolean isCustomMessage) {
        super("ENTITY_NOT_FOUND", message);
    }
}