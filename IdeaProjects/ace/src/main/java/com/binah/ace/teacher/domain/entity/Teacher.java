package com.binah.ace.teacher.domain.entity;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.teacher.domain.enums.ContractType;
import com.binah.ace.teacher.domain.enums.TeacherStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Teacher {
    private UUID id;
    private String fullName;
    private CPF cpf;
    private Email email;
    private String phone;
    private TeacherStatus status;
    private ContractType contractType;
    private Integer workLoadHours; // Carga horaria semanal
    private Set<UUID> subjectIds; // Disciplinas que ensina
    private LocalDate hireDate; // Data contratação
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor sem (ID, timeStamps)
//    public Teacher(String fullName, CPF cpf, Email email, String phone, TeacherStatus status,
//                   ContractType contractType, Integer workLoadHours, LocalDate hireDate) {
//        this.fullName = fullName;
//        this.cpf = cpf;
//        this.email = email;
//        this.phone = phone;
//        this.status = status;
//        this.contractType = contractType;
//        this.workLoadHours = workLoadHours;
//        this.hireDate = hireDate;
//    }


    // Contrutor completo (para reconstruir do banco)
    public Teacher(UUID id, String fullName, CPF cpf, Email email, String phone, TeacherStatus status, ContractType contractType, Integer workLoadHours, Set<UUID> subjectIds,
                   LocalDate hireDate, LocalDateTime createdAt, LocalDateTime updateddAt) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.contractType = contractType;
        this.workLoadHours = workLoadHours;
        this.subjectIds = subjectIds;
        this.hireDate = hireDate;
        this.createdAt = createdAt;
        this.updatedAt = updateddAt;
    }

    // métodos

    // ativar professor
    public void activate() {
        this.status = TeacherStatus.ACTIVE;
        touch();
    }

    // inativar professor
    public void inactivate() {
        this.status = TeacherStatus.INACTIVE;
        touch();
    }

    // colocar de licença
    public void grantLeave() {
        this.status = TeacherStatus.ON_LEAVE;
        touch();
    }

    // desligar professor
    public void terminate() {
        this.status = TeacherStatus.TERMINATED;
        this.subjectIds.clear(); // removendo todas as disciplinas
        touch();
    }

    // // // // //
    // Disciplinas:

    // vincular disciplina
    public void assignSubject(UUID subjectId) {
        // verifica se professor está ativo
        if (!status.canTeach()) {
            throw new IllegalStateException("Professor inativo não pode receber disciplinas.");
        }

        // verififca se a disciplina já foi vinculada
        if (!subjectIds.add((subjectId))) {
            throw new IllegalStateException(("Disciplina já vinculada."));
        }
        touch();
    }

    // Remover disciplina
    public void removeSubject(UUID subjectId) {
        subjectIds.remove(subjectId);
        touch();
    }

    // Atualizações

    // update in cargaHoraria
    public void updateWorkLoad(Integer hours) {
        if (hours == null || hours <= 0 ) {
            throw new IllegalArgumentException("Carga horária deve ser maior que 0.");
        }
        this.workLoadHours = hours;
        touch();
    }

    /*
 - `updatePersonalInfo(name, email, phone)` - Atualizar dados*/

    // update dados pessoais (name, email, phone)
    public void updatePersonalInfo(String fullName, Email email, String phone) {
        if (fullName != null && !fullName.trim().isEmpty()) {
            this.fullName = fullName.trim();
        }

        if (email != null) {
            this.email = email;
        }

        if (phone != null) {
            this.phone = phone;
        }
        touch();
    }

    // verificações
    public boolean canTeach() {
        return status.canTeach();
    }

    // verificações internas
    public void validateCreation(String fullName, CPF cpf, Email email, Integer workLoadHours) {
        if (hireDate == null) { throw new IllegalArgumentException("Data de contratação é obrigatória.");}
        if (hireDate.isAfter(LocalDate.now())) { throw new IllegalArgumentException("Data de contratação não pode ser futura.");}
        if (cpf == null) {throw new IllegalArgumentException("CPF é obrigatório.");}
        if (email == null) {throw new IllegalArgumentException("Email é obrigatório.");}
        if (fullName == null || fullName.trim().isEmpty())
        {throw new IllegalArgumentException("Nome completo é obrigatório.");}
        if(workLoadHours <= 0 || workLoadHours == null)
        {throw new IllegalArgumentException("Carga horária deve ser maior que 0.");}
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    // GETTERS (sem setters públicos - imutabilidade)
    public UUID getId() { return id; }
    public String getFullName() { return fullName; }
    public CPF getCpf() { return cpf; }
    public Email getEmail() { return email; }
    public String getPhone() { return phone; }
    public TeacherStatus getStatus() { return status; }
    public ContractType getContractType() { return contractType; }
    public Integer getWorkLoadHours() { return workLoadHours; }
    public Set<UUID> getSubjectIds() { return new HashSet<>(subjectIds); }
    public LocalDate getHireDate() { return hireDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }






}
