package com.binah.ace.teacher.infrastructure.persistence.jpa;

import com.binah.ace.teacher.domain.enums.TeacherStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherJpaRepository extends
        JpaRepository<TeacherJpaEntity, UUID>{

    // Métodos derivafdos (automaticos)

    Optional<TeacherJpaEntity> findByCpf(String cpf);
    List<TeacherJpaEntity> findByStatus(TeacherStatus status);
    boolean existsByCpf(String cpf);

    // queries customizadas
    // busca de teacher por status
    @Query("SELECT t FROM TeacherJpaEntity t WHERE t.status = " +
            ":status")
    List<TeacherJpaEntity> findActiveTeachers(@Param("status")
    TeacherStatus status);

    // busca de teacher por name
    @Query("SELECT t FROM TeacherJpaEntity t WHERE LOWER(t.fullName) " +
            "LIKE LOWER(CONCAT('%', :name, '%'))")
    List<TeacherJpaEntity> searchByName(@Param("name") String name);

    // busca de professor que contém a disciplina especificada
    @Query("SELECT DISTINCT t FROM TeacherJpaEntity t JOIN t.SubjectIds s WHERE :subjectId MEMBER OF t.subjectIds")
        List<TeacherJpaEntity>
    findBySubjectIdsContaining(@Param("subjectId") UUID subjectId);







}
