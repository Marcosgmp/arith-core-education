# Matriz de Rastreabilidade

Esse documento traz a matriz de rastreabilidade identificando os requisitos funcionais de cada módulo e como se conectam entre si para facilitar a análise de impacto caso algum requisito seja alterado futuramente.

## Simbolos
é utilizado letras para identifica o comportamento de cada requisito

- **D (Depende):** o requisito necessita diretamente do outro para funcionar  
- **R (Relacionado):** os requisitos compartilham escopo ou possuem ligação funcional indireta  
- **— (Sem relação direta):** representa o vazio o requisito não possue relação direta
---
## Módulo de Autenticação e Autorização (AUTH)

### Matriz — AUTH

| Requisito | RF001 | RF002 | RF003 | RF004 | RF005 | RF006 | RF007 |
|-----------|-------|-------|-------|-------|-------|-------|-------|
| **RF001 – Login de Usuário** | — | R | D | R | R | R | D |
| **RF002 – Bloqueio por Tentativas Falhas** | R | — | R | — | R | — | R |
| **RF003 – Controle de Acesso por Roles** | D | R | — | R | — | — | R |
| **RF004 – Logout** | R | — | R | — | — | — | — |
| **RF005 – Recuperação de Senha** | R | R | — | — | — | D | R |
| **RF006 – Alteração de Senha** | R | — | — | — | D | — | R |
| **RF007 – Autenticação em Dois Fatores** | D | R | R | — | R | R | — |

---

## Justificativa da Classificação

### [AUTH.RF001] Login de Usuário

O login é o requisito central do módulo, pois:

- possui relação com **RF002**, porque tentativas inválidas são monitoradas durante o login (**R**)  
- depende de **RF003**, pois após autenticar é necessário validar permissões (**D**)  
- relaciona-se com **RF004**, porque logout encerra a sessão iniciada no login (**R**)  
- relaciona-se com **RF005**, já que recuperação ocorre quando login falha (**R**)  
- relaciona-se com **RF006**, porque nova senha afeta futuras autenticações (**R**)  
- depende de **RF007**, caso o sistema exija verificação em duas etapas (**D**)  

---

### [AUTH.RF002] Bloqueio Automático por Tentativas Falhas

Esse requisito:

- é relacionado ao **RF001**, pois monitora tentativas de login (**R**)  
- é relacionado ao **RF003**, porque usuários bloqueados não podem acessar recursos (**R**)  
- relaciona-se ao **RF005**, já que após bloqueio pode haver recuperação de senha (**R**)  
- relaciona-se ao **RF007**, pois o 2FA reforça segurança junto com bloqueio (**R**)  

---

### [AUTH.RF003] Controle de Acesso por Roles

Esse requisito:

- depende do **RF001**, pois somente usuários autenticados podem ter permissões verificadas (**D**)  
- relaciona-se ao **RF004**, já que ao encerrar sessão o acesso é revogado (**R**)  
- relaciona-se ao **RF007**, pois o 2FA pode ser exigido para perfis sensíveis (**R**)  

---

### [AUTH.RF004] Logout

Esse requisito:

- relaciona-se ao **RF001**, pois só existe após login (**R**)  
- relaciona-se ao **RF003**, porque encerra permissões da sessão atual (**R**)  

---

### [AUTH.RF005] Recuperação de Senha

Esse requisito:

- relaciona-se ao **RF001**, por ser alternativa quando login falha (**R**)  
- relaciona-se ao **RF002**, pois pode desbloquear acesso após falhas (**R**)  
- depende do **RF006**, pois após recuperar normalmente a senha será alterada (**D**)  
- relaciona-se ao **RF007**, porque pode usar validação adicional de segurança (**R**)  

---

### [AUTH.RF006] Alteração de Senha

Esse requisito:

- depende do **RF005**, quando a alteração ocorre após recuperação (**D**)  
- relaciona-se ao **RF001**, pois afeta autenticações futuras (**R**)  
- relaciona-se ao **RF007**, já que pode exigir confirmação por 2FA (**R**)  

---

### [AUTH.RF007] Autenticação de Dois Fatores

Esse requisito:

- depende do **RF001**, pois ocorre após login inicial (**D**)  
- relaciona-se ao **RF002**, por aumentar segurança contra invasões (**R**)  
- relaciona-se ao **RF003**, para proteção de perfis privilegiados (**R**)  
- relaciona-se ao **RF005**, porque pode validar identidade na recuperação (**R**)  
- relaciona-se ao **RF006**, ao confirmar alteração de credenciais (**R**)  

---

## Conclusão Técnica

A análise mostra que o requisito mais crítico do módulo é:

**AUTH.RF001 – Login de Usuário**

Porque ele impacta diretamente quase todos os demais requisitos do módulo.

O segundo requisito mais sensível é:

**AUTH.RF007 – Autenticação em Dois Fatores**

Porque amplia segurança e interfere em múltiplos fluxos de autenticação.


---

## Matriz — STUDENT

| Requisito | RF008 | RF009 | RF010 | RF011 | RF012 | RF013 | RF014 | RF015 | RF016 | RF017 | RF018 | RF019 |
|-----------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **RF008 – Cadastrar Novo Aluno** | — | D | R | R | R | R | — | — | — | — | D | D |
| **RF009 – Gerar Matrícula Automática** | D | — | R | R | R | — | — | — | — | — | — | — |
| **RF010 – Buscar Aluno** | R | R | — | R | R | R | R | R | R | R | R | R |
| **RF011 – Listar Alunos Ativos** | R | R | R | — | R | D | D | R | R | R | — | — |
| **RF012 – Atualizar Dados do Aluno** | R | R | R | R | — | R | R | — | R | — | R | R |
| **RF013 – Inativar Aluno** | R | — | R | D | R | — | D | R | — | R | — | — |
| **RF014 – Reativar Aluno** | — | — | R | D | R | D | — | — | — | — | — | — |
| **RF015 – Graduar Aluno** | — | — | R | R | — | R | — | — | R | — | — | — |
| **RF016 – Transferir Aluno** | — | — | R | R | R | — | — | R | — | — | — | — |
| **RF017 – Expulsar Aluno** | — | — | R | R | — | R | — | — | — | — | — | — |
| **RF018 – Calcular Idade do Aluno** | D | — | R | — | R | — | — | — | — | — | — | D |
| **RF019 – Verificar Maioridade** | D | — | R | — | R | — | — | — | — | — | D | — |

---

## Justificativa da Classificação

### [STUDENT.RF008] Cadastrar Novo Aluno

Este requisito é a base do módulo, pois:

- depende de **RF009**, já que a matrícula é gerada após o cadastro (**D**)  
- relaciona-se com **RF010**, pois o aluno cadastrado poderá ser consultado (**R**)  
- relaciona-se com **RF011**, porque alunos cadastrados passam a compor a lista ativa (**R**)  
- relaciona-se com **RF012**, pois os dados cadastrados podem ser alterados depois (**R**)  
- relaciona-se com **RF013**, porque um aluno precisa existir antes de ser inativado (**R**)  
- depende de **RF018**, para cálculo automático da idade (**D**)  
- depende de **RF019**, para validação de maioridade quando necessário (**D**)  

---

### [STUDENT.RF009] Gerar Matrícula Automática

Esse requisito:

- depende do **RF008**, pois só ocorre após cadastro (**D**)  
- relaciona-se com **RF010**, porque a matrícula pode ser usada na busca (**R**)  
- relaciona-se com **RF011**, já que identifica o aluno nas listagens (**R**)  
- relaciona-se com **RF012**, pois alterações cadastrais mantêm o vínculo da matrícula (**R**)  

---

### [STUDENT.RF010] Buscar Aluno

Esse requisito possui ampla relação com o módulo porque:

- serve como base para localizar alunos antes de atualizar, inativar, transferir ou graduar (**R**)  
- relaciona-se com praticamente todos os requisitos administrativos do módulo (**R**)  

---

### [STUDENT.RF011] Listar Alunos Ativos

Esse requisito:

- relaciona-se com **RF008**, pois exibe alunos cadastrados (**R**)  
- depende de **RF013**, porque a inativação altera essa listagem (**D**)  
- depende de **RF014**, porque a reativação devolve o aluno à lista (**D**)  

---

### [STUDENT.RF012] Atualizar Dados do Aluno

Esse requisito:

- relaciona-se com **RF010**, pois o aluno precisa ser localizado antes da alteração (**R**)  
- relaciona-se com **RF018** e **RF019**, porque alterações na data de nascimento afetam idade e maioridade (**R**)  
- relaciona-se com **RF016**, porque transferência pode exigir atualização cadastral (**R**)  

---

### [STUDENT.RF013] Inativar Aluno

Esse requisito:

- depende de **RF011**, pois remove o aluno da lista de ativos (**D**)  
- depende de **RF014**, já que somente alunos inativos podem ser reativados (**D**)  
- relaciona-se com **RF017**, porque ambos tratam desligamento institucional (**R**)  

---

### [STUDENT.RF014] Reativar Aluno

Esse requisito:

- depende de **RF013**, porque só se reativa quem foi inativado (**D**)  
- depende de **RF011**, pois a reativação impacta a lista de ativos (**D**)  

---

### [STUDENT.RF015] Graduar Aluno

Esse requisito:

- relaciona-se com **RF010**, para localizar o aluno (**R**)  
- relaciona-se com **RF016**, porque transferência e graduação alteram vínculo acadêmico (**R**)  
- relaciona-se com **RF013**, pois alunos graduados podem deixar de estar ativos (**R**)  

---

### [STUDENT.RF016] Transferir Aluno

Esse requisito:

- relaciona-se com **RF010**, pois exige localizar o aluno (**R**)  
- relaciona-se com **RF012**, pois a transferência atualiza dados institucionais (**R**)  
- relaciona-se com **RF015**, porque ambos alteram situação acadêmica (**R**)  

---

### [STUDENT.RF017] Expulsar Aluno

Esse requisito:

- relaciona-se com **RF010**, para localizar o aluno (**R**)  
- relaciona-se com **RF013**, pois ambos removem o aluno do status ativo (**R**)  

---

### [STUDENT.RF018] Calcular Idade do Aluno

Esse requisito:

- depende de **RF008**, pois utiliza a data de nascimento cadastrada (**D**)  
- depende de **RF019**, pois a idade é usada para verificar maioridade (**D**)  
- relaciona-se com **RF012**, quando a data de nascimento é alterada (**R**)  

---

### [STUDENT.RF019] Verificar Maioridade

Esse requisito:

- depende de **RF018**, porque precisa da idade calculada (**D**)  
- depende de **RF008**, porque utiliza os dados cadastrais do aluno (**D**)  
- relaciona-se com **RF012**, caso a data de nascimento seja modificada (**R**)  

---

# Conclusão Técnica

Os requisitos mais críticos deste módulo são:

**STUDENT.RF008 – Cadastrar Novo Aluno**  
Porque serve como base para quase todas as demais funcionalidades.

**STUDENT.RF010 – Buscar Aluno**  
Porque é utilizado como apoio operacional em praticamente todos os processos administrativos.

**STUDENT.RF012 – Atualizar Dados do Aluno**  
Porque impacta múltiplas funcionalidades relacionadas ao ciclo de vida acadêmico.


---

## Matriz — GRADES

| Requisito | RF020 | RF021 | RF022 | RF023 | RF024 | RF025 | RF026 |
|-----------|-------|-------|-------|-------|-------|-------|-------|
| **RF020 – Lançar Nota** | — | D | R | D | R | R | D |
| **RF021 – Atualizar Nota** | D | — | R | R | R | R | D |
| **RF022 – Adicionar Comentário em Nota** | R | R | — | — | R | R | — |
| **RF023 – Calcular GPA do Aluno** | D | R | — | — | D | — | D |
| **RF024 – Listar Notas por Aluno** | R | R | R | D | — | — | R |
| **RF025 – Listar Notas por Turma** | R | R | R | — | — | — | R |
| **RF026 – Validar Status do Aluno** | D | D | — | D | R | R | — |

---

## Justificativa da Classificação

### [GRADES.RF020] Lançar Nota

Esse requisito é central porque:

- depende de **RF026**, pois o aluno precisa estar apto para receber nota (**D**)  
- depende de **RF021**, já que notas lançadas podem ser alteradas depois (**D**)  
- relaciona-se com **RF022**, para inclusão de observações na avaliação (**R**)  
- depende de **RF023**, pois a nota influencia o GPA (**D**)  
- relaciona-se com **RF024** e **RF025**, pois alimenta as listagens (**R**)  

---

### [GRADES.RF021] Atualizar Nota

Esse requisito:

- depende de **RF020**, pois só é possível atualizar nota já lançada (**D**)  
- relaciona-se com **RF022**, porque comentários podem acompanhar a alteração (**R**)  
- impacta **RF023**, alterando o cálculo do GPA (**R**)  
- relaciona-se com as consultas de notas (**R**)  
- depende de **RF026**, pois o status do aluno pode restringir alteração (**D**)  

---

### [GRADES.RF022] Adicionar Comentário em Nota

Esse requisito:

- relaciona-se com **RF020** e **RF021**, pois complementa registros de nota (**R**)  
- relaciona-se com **RF024** e **RF025**, para exibição ao consultar notas (**R**)  

---

### [GRADES.RF023] Calcular GPA do Aluno

Esse requisito:

- depende das notas lançadas em **RF020** (**D**)  
- relaciona-se com **RF021**, pois alterações impactam o cálculo (**R**)  
- depende de **RF024**, já que usa o histórico do aluno (**D**)  
- depende de **RF026**, considerando apenas alunos válidos (**D**)  

---

### [GRADES.RF024] Listar Notas por Aluno

Esse requisito:

- relaciona-se com lançamento, atualização e comentários (**R**)  
- depende de **RF023**, quando exibe GPA consolidado (**D**)  
- relaciona-se com **RF026**, para validar acesso aos dados (**R**)  

---

### [GRADES.RF025] Listar Notas por Turma

Esse requisito:

- relaciona-se com os registros de nota (**R**)  
- relaciona-se com **RF026**, pois considera apenas alunos ativos (**R**)  

---

### [GRADES.RF026] Validar Status do Aluno

Esse requisito:

- depende do contexto de lançamento e atualização (**D**)  
- depende do cálculo do GPA (**D**)  
- relaciona-se com consultas por aluno e turma (**R**)  

---

# Conclusão Técnica

Os requisitos mais críticos são:

**GRADES.RF020 – Lançar Nota**  
Porque inicia todo o fluxo do módulo.

**GRADES.RF023 – Calcular GPA do Aluno**  
Porque consolida o desempenho acadêmico.

---


## Matriz — ATTENDANCE

| Requisito | RF027 | RF028 | RF029 | RF030 | RF031 | RF032 | RF033 |
|-----------|-------|-------|-------|-------|-------|-------|-------|
| **RF027 – Registrar Presença** | — | D | R | D | D | R | R |
| **RF028 – Atualizar Presença** | D | — | R | R | R | R | R |
| **RF029 – Justificar Falta** | R | R | — | R | R | R | — |
| **RF030 – Calcular Percentual de Presença** | D | R | R | — | D | D | D |
| **RF031 – Alertar Baixa Frequência** | D | R | R | D | — | R | R |
| **RF032 – Listar Frequência por Aluno** | R | R | R | D | R | — | — |
| **RF033 – Listar Frequência por Turma** | R | R | — | D | R | — | — |

---

## Justificativa da Classificação

### [ATTENDANCE.RF027] Registrar Presença

Esse requisito é o núcleo do módulo porque:

- depende de **RF028**, pois presenças podem ser corrigidas (**D**)  
- relaciona-se com **RF029**, em caso de faltas justificadas (**R**)  
- depende de **RF030**, pois compõe o percentual final (**D**)  
- depende de **RF031**, porque gera alertas futuros (**D**)  
- alimenta as listagens (**R**)  

---

### [ATTENDANCE.RF028] Atualizar Presença

Esse requisito:

- depende de **RF027**, pois exige registro anterior (**D**)  
- relaciona-se com justificativas (**R**)  
- impacta percentual e alertas (**R**)  
- afeta relatórios de frequência (**R**)  

---

### [ATTENDANCE.RF029] Justificar Falta

Esse requisito:

- relaciona-se com registros e atualizações (**R**)  
- influencia o percentual de presença (**R**)  
- pode reduzir alertas indevidos (**R**)  

---

### [ATTENDANCE.RF030] Calcular Percentual de Presença

Esse requisito:

- depende de **RF027** (**D**)  
- relaciona-se com **RF028** e **RF029** (**R**)  
- depende de **RF031**, pois o alerta usa esse cálculo (**D**)  
- depende das listagens por aluno e turma (**D**)  

---

### [ATTENDANCE.RF031] Alertar Baixa Frequência

Esse requisito:

- depende do cálculo em **RF030** (**D**)  
- relaciona-se com justificativas (**R**)  
- relaciona-se com relatórios (**R**)  

---

### [ATTENDANCE.RF032] Listar Frequência por Aluno

Esse requisito:

- relaciona-se com registros e alterações (**R**)  
- depende de **RF030**, para mostrar percentual consolidado (**D**)  

---

### [ATTENDANCE.RF033] Listar Frequência por Turma

Esse requisito:

- relaciona-se com os registros de presença (**R**)  
- depende de **RF030**, para consolidar frequência da turma (**D**)  

---

# Conclusão Técnica

Os requisitos mais críticos são:

**ATTENDANCE.RF027 – Registrar Presença**  
Porque inicia o fluxo operacional do módulo.

**ATTENDANCE.RF030 – Calcular Percentual de Presença**  
Porque impacta alertas e relatórios do sistema.


---

## Matriz — TEACHER

| Requisito | RF034 | RF035 | RF036 | RF037 | RF038 | RF039 | RF040 | RF041 | RF042 | RF043 |
|-----------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **RF034 – Cadastrar Professor** | — | D | — | R | R | R | R | R | R | — |
| **RF035 – Vincular Professor a Disciplina** | D | — | D | R | R | R | — | — | R | D |
| **RF036 – Desvincular Professor de Disciplina** | — | D | — | — | R | — | — | — | R | D |
| **RF037 – Atualizar Dados do Professor** | R | R | — | — | R | R | — | R | R | — |
| **RF038 – Inativar Professor** | R | R | R | R | — | R | R | D | R | — |
| **RF039 – Colocar Professor em Licença** | R | R | — | R | R | — | — | D | R | — |
| **RF040 – Desligar Professor** | R | — | — | — | R | — | — | D | R | — |
| **RF041 – Listar Professores Ativos** | R | — | — | R | D | D | D | — | R | — |
| **RF042 – Buscar Professor** | R | R | R | R | R | R | R | R | — | R |
| **RF043 – Listar Professores por Disciplina** | — | D | D | — | — | — | — | — | R | — |

---

# Justificativa da Classificação

### [TEACHER.RF034] Cadastrar Professor

Este requisito é a base do módulo porque:

- depende de **RF035**, para permitir vinculação posterior (**D**)  
- relaciona-se com atualização, inativação, licença e desligamento (**R**)  
- alimenta listagens e consultas (**R**)  

---

### [TEACHER.RF035] Vincular Professor a Disciplina

Esse requisito:

- depende de **RF034**, pois o professor precisa existir (**D**)  
- depende de **RF036**, quando houver remoção posterior (**D**)  
- depende de **RF043**, pois influencia listagem por disciplina (**D**)  

---

### [TEACHER.RF038] Inativar Professor

Esse requisito:

- altera diretamente **RF041**, removendo o professor da lista de ativos (**D**)  
- relaciona-se com desligamento e licença (**R**)  

---

### [TEACHER.RF042] Buscar Professor

Esse requisito:

- serve de apoio para quase todas as operações administrativas (**R**)  

---

# Conclusão Técnica

Os requisitos mais críticos são:

**TEACHER.RF034 – Cadastrar Professor**  
**TEACHER.RF035 – Vincular Professor a Disciplina**  
**TEACHER.RF042 – Buscar Professor**


---

## Matriz — SUBJECT

| Requisito | RF044 | RF045 | RF046 | RF047 | RF048 | RF049 |
|-----------|-------|-------|-------|-------|-------|-------|
| **RF044 – Cadastrar Disciplina** | — | D | R | R | R | R |
| **RF045 – Atualizar Disciplina** | D | — | R | — | R | R |
| **RF046 – Ativar/Desativar Disciplina** | R | R | — | R | D | R |
| **RF047 – Arquivar Disciplina** | R | — | R | — | D | R |
| **RF048 – Listar Disciplinas Ativas** | R | R | D | D | — | R |
| **RF049 – Buscar Disciplina** | R | R | R | R | R | — |

---

# Justificativa da Classificação

### [SUBJECT.RF044] Cadastrar Disciplina

Esse requisito:

- depende de **RF045** para futuras alterações (**D**)  
- relaciona-se com ativação e arquivamento (**R**)  
- alimenta listagens e consultas (**R**)  

---

### [SUBJECT.RF046] Ativar/Desativar Disciplina

Esse requisito:

- impacta diretamente **RF048**, pois altera a lista de disciplinas ativas (**D**)  
- relaciona-se com arquivamento (**R**)  

---

### [SUBJECT.RF049] Buscar Disciplina

Esse requisito:

- apoia praticamente todas as operações do módulo (**R**)  

---

# Conclusão Técnica

Os requisitos mais críticos são:

**SUBJECT.RF044 – Cadastrar Disciplina**  
**SUBJECT.RF046 – Ativar/Desativar Disciplina**  
**SUBJECT.RF049 – Buscar Disciplina**

---

## Matriz — CLASSROOM

| Requisito | RF050 | RF051 | RF052 | RF053 | RF054 | RF055 | RF056 | RF057 | RF058 |
|-----------|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **RF050 – Criar Turma** | — | D | — | D | R | R | R | R | D |
| **RF051 – Matricular Aluno em Turma** | D | — | D | — | D | D | — | — | D |
| **RF052 – Remover Aluno de Turma** | — | D | — | — | D | D | — | — | — |
| **RF053 – Alterar Professor da Turma** | D | — | — | — | — | — | D | — | D |
| **RF054 – Listar Alunos da Turma** | R | D | D | — | — | — | — | — | — |
| **RF055 – Listar Turmas do Aluno** | R | D | D | — | — | — | — | — | — |
| **RF056 – Listar Turmas do Professor** | R | — | — | D | — | — | — | — | — |
| **RF057 – Fechar Turma** | R | — | — | — | — | — | — | — | R |
| **RF058 – Verificar Conflito de Horário** | D | D | — | D | — | — | — | R | — |

---

## Justificativa da Classificação

# [CLASSROOM.RF050] Criar Turma

Esse requisito é a base do módulo porque:

- depende de matrícula de alunos (**RF051**) (**D**)  
- depende da definição do professor (**RF053**) (**D**)  
- depende da verificação de conflito de horário (**RF058**) (**D**)  
- alimenta todas as listagens (**R**)  

---

# [CLASSROOM.RF051] Matricular Aluno em Turma

Esse requisito:

- depende da turma criada (**RF050**) (**D**)  
- depende da remoção posterior (**RF052**) (**D**)  
- impacta listagem de alunos e turmas (**D**)  
- depende da validação de horários (**RF058**) (**D**)  

---

### [CLASSROOM.RF053] Alterar Professor da Turma

Esse requisito:

- depende da turma existir (**RF050**) (**D**)  
- impacta listagem das turmas do professor (**RF056**) (**D**)  
- depende da verificação de conflitos (**RF058**) (**D**)  

---

### [CLASSROOM.RF058] Verificar Conflito de Horário

Esse requisito:

- depende da criação da turma (**RF050**)  
- depende da matrícula (**RF051**)  
- depende da troca de professor (**RF053**)  
- relaciona-se ao fechamento da turma (**RF057**)  

---

# Conclusão Técnica

Os requisitos mais críticos são:

**CLASSROOM.RF050 – Criar Turma**  
**CLASSROOM.RF051 – Matricular Aluno em Turma**  
**CLASSROOM.RF058 – Verificar Conflito de Horário**

---

## Matriz — REPORT

| Requisito | RF059 | RF060 | RF061 | RF062 | RF063 | RF064 | RF065 |
|-----------|-------|-------|-------|-------|-------|-------|-------|
| **RF059 – Gerar Boletim Individual** | — | R | R | D | R | R | D |
| **RF060 – Gerar Boletim por Turma** | R | — | R | — | R | R | R |
| **RF061 – Gerar Relatório de Frequência** | R | R | — | R | — | D | — |
| **RF062 – Gerar Histórico Acadêmico Completo** | D | — | R | — | R | — | R |
| **RF063 – Relatório de Desempenho por Disciplina** | R | R | — | R | — | D | — |
| **RF064 – Relatório de Alunos em Risco** | R | R | D | — | D | — | R |
| **RF065 – Enviar Boletim por Email** | D | R | — | R | — | R | — |

---

## Justificativa da Classificação

### [REPORT.RF059] Gerar Boletim Individual

Esse requisito:

- depende de **RF062**, pois usa o histórico acadêmico consolidado (**D**)  
- depende de **RF065**, quando o boletim será enviado por e-mail (**D**)  
- relaciona-se com boletins por turma e relatórios de desempenho (**R**)  

---

### [REPORT.RF061] Gerar Relatório de Frequência

Esse requisito:

- relaciona-se com boletins e histórico (**R**)  
- depende de **RF064**, pois ajuda a identificar alunos em risco (**D**)  

---

### [REPORT.RF064] Gerar Relatório de Alunos em Risco

Esse requisito:

- depende de **RF061**, usando frequência baixa (**D**)  
- depende de **RF063**, usando desempenho acadêmico (**D**)  

---

### [REPORT.RF065] Enviar Boletim por Email

Esse requisito:

- depende de **RF059**, pois precisa do boletim individual gerado (**D**)  
- relaciona-se com outros relatórios compartilháveis (**R**)  

---

# Conclusão Técnica

Os requisitos mais críticos são:

**REPORT.RF059 – Gerar Boletim Individual**  
**REPORT.RF064 – Gerar Relatório de Alunos em Risco**

---

## Matriz — NOTIFICATION

| Requisito | RF066 | RF067 | RF068 | RF069 | RF070 | RF071 |
|-----------|-------|-------|-------|-------|-------|-------|
| **RF066 – Enviar Email de Boas-Vindas** | — | — | — | R | — | D |
| **RF067 – Notificar Lançamento de Nota** | — | — | — | R | D | D |
| **RF068 – Alertar Baixa Frequência** | — | — | — | R | — | D |
| **RF069 – Enviar Comunicados Gerais** | R | R | R | — | R | D |
| **RF070 – Notificar Alteração de Nota** | — | D | — | R | — | D |
| **RF071 – Configurar Preferências de Notificação** | D | D | D | D | D | — |

---

## Justificativa da Classificação

### [NOTIFICATION.RF071] Configurar Preferências de Notificação

Esse requisito é central porque:

- todos os envios dependem da preferência definida pelo usuário (**D**)  
- controla canais e tipos de mensagens permitidas (**D**)  

---

### [NOTIFICATION.RF067] Notificar Lançamento de Nota

Esse requisito:

- depende de **RF070**, pois alterações posteriores podem gerar nova notificação (**D**)  
- depende de **RF071**, para verificar se o aluno deseja receber aviso (**D**)  

---

### [NOTIFICATION.RF069] Enviar Comunicados Gerais

Esse requisito:

- relaciona-se com todos os tipos de mensagens do módulo (**R**)  
- depende de **RF071**, para respeitar preferências (**D**)  

---

# Conclusão Técnica

Os requisitos mais críticos são:

**NOTIFICATION.RF071 – Configurar Preferências de Notificação**  
**NOTIFICATION.RF067 – Notificar Lançamento de Nota**

---


## Matriz — AUDIT

| Requisito | RF072 | RF073 | RF074 | RF075 | RF076 | RF077 | RF078 | RF079 |
|-----------|-------|-------|-------|-------|-------|-------|-------|-------|
| **RF072 – Registrar Criação de Aluno** | — | — | — | R | — | R | R | D |
| **RF073 – Registrar Lançamento de Nota** | — | — | D | — | — | R | R | D |
| **RF074 – Registrar Alteração de Nota** | — | D | — | — | — | R | R | D |
| **RF075 – Registrar Mudança de Status do Aluno** | R | — | — | — | — | R | R | D |
| **RF076 – Registrar Desligamento de Professor** | — | — | — | — | — | R | R | D |
| **RF077 – Consultar Logs de Auditoria** | R | R | R | R | R | — | D | R |
| **RF078 – Exportar Logs de Auditoria** | R | R | R | R | R | D | — | R |
| **RF079 – Conformidade com LGPD** | D | D | D | D | D | R | R | — |

---

# Justificativa da Classificação

### [AUDIT.RF079] Conformidade com LGPD

Esse requisito é o mais abrangente porque:

- depende de todos os registros anteriores para garantir rastreabilidade legal (**D**)  
- relaciona-se com consulta e exportação dos logs (**R**)  

---

### [AUDIT.RF077] Consultar Logs de Auditoria

Esse requisito:

- relaciona-se com todos os registros do módulo (**R**)  
- depende de **RF078**, quando os dados precisam ser exportados (**D**)  

---

### [AUDIT.RF078] Exportar Logs de Auditoria

Esse requisito:

- depende de **RF077**, pois a exportação ocorre após consulta (**D**)  
- relaciona-se com conformidade regulatória (**R**)  

---

# Conclusão Técnica

Os requisitos mais críticos são:

**AUDIT.RF079 – Conformidade com LGPD**  
**AUDIT.RF077 – Consultar Logs de Auditoria**