package com.binah.ace.teacher.infrastructure.persistence;

import com.binah.ace.teacher.domain.entity.Teacher;

public class TeacherMapper {

    public static TeacherJpaEntity toJpa(Teacher teacher) {
        TeacherJpaEntity entity = new TeacherJpaEntity();
        entity.setId(teacher.getId());
        entity.setName(teacher.getName());
        entity.setEmail(teacher.getEmail());
        entity.setDegree(teacher.getDegree());
        return entity;
    }

    public static Teacher toDomain(TeacherJpaEntity entity) {
        return new Teacher(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getDegree()
        );
    }

}
