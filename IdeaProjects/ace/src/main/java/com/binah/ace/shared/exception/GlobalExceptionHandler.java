package com.binah.ace.shared.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

/**
 * Handler global para traduzir exceções Java em erros GraphQL.
 *
 * Converte exceções do sistema (BusinessException, EntityNotFoundException, etc)
 * em respostas GraphQL padronizadas com códigos de erro apropriados.
 *
 * NÃO contém regras de negócio - apenas tradução de erros.
 *
 * Tipos de erro GraphQL:
 * - NOT_FOUND (404): Entidade não encontrada
 * - FORBIDDEN (403): Sem permissão
 * - BAD_REQUEST (400): Erro de validação ou regra de negócio
 * - UNAUTHORIZED (401): Não autenticado
 * - INTERNAL_ERROR (500): Erro interno do servidor
 *
 * @author Marcos Gustavo
 */
@Component
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(
            Throwable ex,
            DataFetchingEnvironment env
    ){
        //Entidade não encontrada = NOT_Found (404)
      if(ex instanceof EntityNotFoundException){
          return GraphqlErrorBuilder.newError(env)
                  .errorType(ErrorType.NOT_FOUND)
                  .message(ex.getMessage())
                  .path(env.getExecutionStepInfo().getPath())
                  .location(env.getField().getSourceLocation())
                  .build();
      }

      // Sem permissão -> FORBIDDEN (403)
      if(ex instanceof PermissionDeniedException){
          return GraphqlErrorBuilder.newError(env)
                  .errorType(ErrorType.FORBIDDEN)
                  .message(ex.getMessage())
                  .path(env.getExecutionStepInfo().getPath())
                  .location(env.getField().getSourceLocation())
                  .build();
      }

      // Regra de negócio violada -> BAD_REQUEST (400)
      if(ex instanceof BusinessException){
          BusinessException businessEx =(BusinessException) ex;
          return GraphqlErrorBuilder.newError(env)
                  .errorType(ErrorType.BAD_REQUEST)
                  .message(ex.getMessage())
                  .path(env.getExecutionStepInfo().getPath())
                  .location(env.getField().getSourceLocation())
                  .extensions(java.util.Map.of("code", businessEx.getCode()))
                  .build();
      }

        // Argumento inválido → BAD_REQUEST (400)
        if (ex instanceof IllegalArgumentException) {
            return GraphqlErrorBuilder.newError(env)
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }

        // Erro não mapeado → INTERNAL_ERROR (500)
        // NÃO expõe detalhes internos ao cliente
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.INTERNAL_ERROR)
                .message("An unexpected error occurred")
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
