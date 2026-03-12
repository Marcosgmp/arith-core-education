package com.binah.ace.teacher.infrastructure.persistence.mapper;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.teacher.infrastructure.persistence.jpa.TeacherJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherMapper {

    // JPA -> DOMAIN
    public Teacher toDomain(TeacherJpaEntity entity) {
        if (entity == null) return null;

        return new Teacher(
            entity.getId(),
            entity.getFullName(),
            new CPF(entity.getCpf()),
            new Email(entity.getEmail()),
            entity.getPhone(),
            entity.getStatus(),
            entity.getContractType(),
            entity.getWorkLoadHours(),
            entity.getSubjectIds(),
            entity.getHireDate(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }


    // DOMAIN -> JPA
    public TeacherJpaEntity toJpa(Teacher domain) {
        if (domain == null) return null;

        TeacherJpaEntity entity = new TeacherJpaEntity();
        entity.setId(domain.getId());
        entity.setFullName(domain.getFullName());
        entity.setCpf(domain.getCpf().toString());
        entity.setEmail(domain.getEmail().toString());
        entity.setPhone(domain.getPhone());
        entity.setStatus(domain.getStatus());
        entity.setContractType(domain.getContractType());
        entity.setWorkLoadHours(domain.getWorkloadHours());
        entity.setSubjectIds(domain.getSubjectIds());
        entity.setHireDate(domain.getHireDate());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }

    // Listas
    public List<Teacher> toDomainList(List<TeacherJpaEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public List<TeacherJpaEntity> toJpaList(List<Teacher> domains) {
        return domains.stream()
                .map(this::toJpa)
                .collect(Collectors.toList());
    }
}