package com.binah.ace.teacher.infrastructure.persistence;

/*
* Injeta no JPA
* */

import com.binah.ace.teacher.domain.repository.TeacherRepository;
import com.binah.ace.teacher.infrastructure.persistence.mapper.TeacherMapper;
import com.binah.ace.teacher.infrastructure.persistence.jpa.TeacherJpaRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeacherRepositoryConfig {

    @Bean
    public TeacherRepository teacherRepository(
            TeacherJpaRepository jpaRepository,
            TeacherMapper mapper) {
        return new TeacherRepositoryImpl(jpaRepository, mapper);
    }
}
