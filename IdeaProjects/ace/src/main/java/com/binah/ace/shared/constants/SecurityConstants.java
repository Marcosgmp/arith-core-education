package com.binah.ace.shared.constants;

/**
 * Constantes de segurança do sistema ACE.
 *
 * Centraliza todas as configurações relacionadas a:
 * - JWT (JSON Web Token)
 * - Roles (papéis de usuário)
 * - Permissions (permissões granulares)
 * - Password (políticas de senha)
 * - Login (tentativas, bloqueio)
 *
 * IMPORTANTE:
 * - JWT_SECRET deve ser sobrescrito em produção via application.properties
 * - Não commitar secrets reais no código
 *
 * @author Marcos Gustavo
 */
public final class SecurityConstants {

    /**
     * Chave secreta para assinar tokens JWT.
     *
     * ATENÇÃO: Esta é apenas uma chave de desenvolvimento!
     * Em produção, use uma chave forte e armazene em variável de ambiente.
     *
     * Configure em application.properties:
     * jwt.secret=sua-chave-super-secreta-aqui-minimo-256-bits
     */
    public static final String JWT_SECRET_KEY =
            "ace-secret-key-change-in-production-minimum-256-bits";

    /**
     * Tempo de expiração do token JWT em milissegundos.
     *
     * Valor atual: 86.400.000 ms = 24 horas
     */
    public static final long JWT_EXPIRATION_MS = 86_400_000L; // 24 horas

    /**
     * Prefixo do token JWT no header Authorization.
     *
     * Exemplo: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
     */
    public static final String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * Nome do header HTTP que contém o token.
     *
     * Padrão: Authorization
     */
    public static final String JWT_HEADER_STRING = "Authorization";

    /**
     * Claim customizado para armazenar o tipo de usuário no token.
     */
    public static final String JWT_CLAIM_USER_TYPE = "userType";

    /**
     * Claim customizado para armazenar o ID da entidade relacionada.
     *
     * Exemplos:
     * - Se user_type = STUDENT → entity_id = ID do student
     * - Se user_type = TEACHER → entity_id = ID do teacher
     */
    public static final String JWT_CLAIM_ENTITY_ID = "entityId";


    /**
     * Role: Administrador do sistema.
     *
     * Permissões: TODAS
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Role: Professor.
     *
     * Permissões: Gerenciar turmas, lançar notas, registrar presença
     */
    public static final String ROLE_TEACHER = "ROLE_TEACHER";

    /**
     * Role: Aluno.
     *
     * Permissões: Visualizar próprias notas, presenças, boletim
     */
    public static final String ROLE_STUDENT = "ROLE_STUDENT";

    /**
     * Role: Responsável/Guardião.
     *
     * Permissões: Visualizar dados do aluno vinculado
     */
    public static final String ROLE_GUARDIAN = "ROLE_GUARDIAN";

    /**
     * Role: Equipe administrativa (secretaria).
     *
     * Permissões: Criar alunos, matrículas, emitir relatórios
     */
    public static final String ROLE_STAFF = "ROLE_STAFF";

    // ========================================
    // PERMISSIONS - Permissões Granulares
    // ========================================

    // Student Permissions
    public static final String PERMISSION_CREATE_STUDENT = "CREATE_STUDENT";
    public static final String PERMISSION_VIEW_STUDENT = "VIEW_STUDENT";
    public static final String PERMISSION_UPDATE_STUDENT = "UPDATE_STUDENT";
    public static final String PERMISSION_DELETE_STUDENT = "DELETE_STUDENT";

    // Teacher Permissions
    public static final String PERMISSION_CREATE_TEACHER = "CREATE_TEACHER";
    public static final String PERMISSION_VIEW_TEACHER = "VIEW_TEACHER";
    public static final String PERMISSION_UPDATE_TEACHER = "UPDATE_TEACHER";
    public static final String PERMISSION_DELETE_TEACHER = "DELETE_TEACHER";

    // Grade Permissions
    public static final String PERMISSION_POST_GRADE = "POST_GRADE";
    public static final String PERMISSION_UPDATE_GRADE = "UPDATE_GRADE";
    public static final String PERMISSION_DELETE_GRADE = "DELETE_GRADE";
    public static final String PERMISSION_VIEW_GRADE = "VIEW_GRADE";

    // Attendance Permissions
    public static final String PERMISSION_RECORD_ATTENDANCE = "RECORD_ATTENDANCE";
    public static final String PERMISSION_UPDATE_ATTENDANCE = "UPDATE_ATTENDANCE";
    public static final String PERMISSION_VIEW_ATTENDANCE = "VIEW_ATTENDANCE";

    // Classroom Permissions
    public static final String PERMISSION_CREATE_CLASSROOM = "CREATE_CLASSROOM";
    public static final String PERMISSION_VIEW_CLASSROOM = "VIEW_CLASSROOM";
    public static final String PERMISSION_UPDATE_CLASSROOM = "UPDATE_CLASSROOM";
    public static final String PERMISSION_DELETE_CLASSROOM = "DELETE_CLASSROOM";

    // Enrollment Permissions
    public static final String PERMISSION_ENROLL_STUDENT = "ENROLL_STUDENT";
    public static final String PERMISSION_UNENROLL_STUDENT = "UNENROLL_STUDENT";

    // Report Permissions
    public static final String PERMISSION_VIEW_REPORT_CARD = "VIEW_REPORT_CARD";
    public static final String PERMISSION_GENERATE_REPORTS = "GENERATE_REPORTS";

    /**
     * Tamanho mínimo da senha.
     *
     * Valor: 8 caracteres
     */
    public static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * Tamanho máximo da senha.
     *
     * Valor: 128 caracteres (evita ataques de DoS)
     */
    public static final int MAX_PASSWORD_LENGTH = 128;

    /**
     * Regex para validar força da senha.
     *
     * Requisitos:
     * - Mínimo 8 caracteres
     * - Pelo menos 1 letra maiúscula
     * - Pelo menos 1 letra minúscula
     * - Pelo menos 1 número
     * - Pelo menos 1 caractere especial
     */
    public static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";


    /**
     * Número máximo de tentativas de login falhas antes de bloquear a conta.
     *
     * Valor: 5 tentativas
     */
    public static final int MAX_LOGIN_ATTEMPTS = 5;

    /**
     * Duração do bloqueio da conta em minutos após exceder tentativas.
     *
     * Valor: 30 minutos
     */
    public static final int ACCOUNT_LOCK_DURATION_MINUTES = 30;

    /**
     * Tempo de reset automático das tentativas falhas (em horas).
     *
     * Se não houver tentativa por 24h, o contador reseta.
     *
     * Valor: 24 horas
     */
    public static final int FAILED_ATTEMPTS_RESET_HOURS = 24;


    /**
     * Número máximo de sessões simultâneas por usuário.
     *
     * Valor: 3 (ex: celular, tablet, computador)
     */
    public static final int MAX_SESSIONS_PER_USER = 3;

    /**
     * Tempo de inatividade antes de considerar sessão expirada (em minutos).
     *
     * Valor: 30 minutos
     */
    public static final int SESSION_TIMEOUT_MINUTES = 30;


    /**
     * Construtor privado para evitar instanciação.
     *
     * Esta é uma classe de constantes (utility class).
     */
    private SecurityConstants() {
        throw new UnsupportedOperationException(
                "SecurityConstants is a utility class and cannot be instantiated"
        );
    }
}
