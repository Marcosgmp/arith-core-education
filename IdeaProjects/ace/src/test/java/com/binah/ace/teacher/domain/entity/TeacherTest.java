package com.binah.ace.teacher.domain.entity;

import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.shared.valueobject.Email;
import com.binah.ace.teacher.domain.enums.ContractType;
import com.binah.ace.teacher.domain.enums.TeacherStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    // code for tests : .\mvnw.cmd test -Dtest=TeacherTest

    @Test
    void createTeacher_Success() {
        CPF cpf = new CPF("529.982.247-25");
        Email email = new Email("joao@school.com");

        Teacher teacher = new Teacher("João Silva", cpf, email, "11999999999", TeacherStatus.ACTIVE,
                ContractType.FULL_TIME, Integer.valueOf(40), LocalDate.now());

        assertEquals(TeacherStatus.ACTIVE, teacher.getStatus());
        assertTrue(teacher.canTeach());
        assertEquals("João Silva", teacher.getFullName());
        assertEquals(40, teacher.getWorkloadHours());
    }
}
