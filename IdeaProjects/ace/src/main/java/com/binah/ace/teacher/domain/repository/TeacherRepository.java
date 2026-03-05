package com.binah.ace.teacher.domain.repository;

import com.binah.ace.teacher.domain.entity.Teacher;

import java.util.Optional;
import java.util.UUID;


public interface TeacherRepository {

    Teacher save(Teacher teacher);

    Optional<Teacher> findByid(UUID id);
}
