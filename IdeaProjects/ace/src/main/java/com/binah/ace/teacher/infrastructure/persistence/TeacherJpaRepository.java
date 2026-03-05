package com.binah.ace.teacher.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TeacherJpaRepository extends JpaRepository<TeacherJpaEntity, UUID>{

}
