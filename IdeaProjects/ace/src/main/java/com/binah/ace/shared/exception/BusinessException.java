package com.binah.ace.shared.exception;

/**
 * Classe base para todas as exceções de negócio do sistema ACE.
 *
 * Todas as exceções relacionadas a regras de negócio devem estender esta classe.
 * Fornece um código de erro para facilitar o tratamento no front-end.
 *
 * @author Marcos Gustavo
 */
public class BusinessException extends RuntimeException {

    private final String code;

    /**
     * Construtor com código e mensagem.
     *
     * @param code Código identificador do erro (ex: "STUDENT_NOT_FOUND")
     * @param message Mensagem descritiva do erro
     */
    public BusinessException(String code,String message) {

        super(message);
        this.code = code;

    }

    /**
     * Construtor com código, mensagem e causa.
     * @param code Código identificador do erro (ex: "STUDENT_NOT_FOUND")
     * @param message Mensagem descritiva do erro
     * @param cause causa do erro
     */
    public BusinessException(String code,String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }

    /**
     * Retorna o erro do código
     * @return código do erro
     */
    public String getCode(){
        return code;
    }
}