package com.binah.ace.teacher.infrastructure.persistence;


import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.teacher.domain.entity.Teacher;
import com.binah.ace.teacher.domain.enums.TeacherStatus;
import com.binah.ace.teacher.domain.exception.TeacherNotFoundException;
import com.binah.ace.teacher.domain.repository.TeacherRepository;
import com.binah.ace.teacher.infrastructure.persistence.jpa.TeacherJpaRepository;
import com.binah.ace.teacher.infrastructure.persistence.mapper.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 ** RESUMO
 ** BASICAMENTE O TeacherRepositoryImpl COLA TUDO DO DOMAIN NO JPA
*/

@Repository
@RequiredArgsConstructor
public class TeacherRepositoryImpl implements TeacherRepository {

    private final TeacherJpaRepository jpaRepository;
    private final TeacherMapper mapper;

    @Override
    public Optional<Teacher> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Teacher> findByCPF(CPF cpf) {
        return jpaRepository.findByCpf(cpf.toString())  // CPF → String
                .map(mapper::toDomain);
    }

    @Override
    public List<Teacher> findByStatus(TeacherStatus status) {
        return 
            mapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    public boolean existsByCPF(CPF cpf) {
        return jpaRepository.existsByCpf(cpf.toString());
    }

    @Override
    public Teacher save(Teacher teacher) {
        var entity = mapper.toJpa(teacher);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        if (!jpaRepository.existsById(id)) {
            throw new TeacherNotFoundException(id);
        }
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Teacher> searchByName(String name) {
        return mapper.toDomainList(jpaRepository.searchByName(name));
    }

    @Override
    public List<Teacher> findBySubjectId(UUID subjectId) {
        return
                mapper.toDomainList(jpaRepository.findBySubjectIdsContaining(subjectId));
    }
    @Override
    public List<Teacher> findActiveTeachers() {
        return mapper.toDomainList(
                jpaRepository.findActiveTeachers(TeacherStatus.ACTIVE)
        );
    }


}
