package com.binah.ace.teacher.domain.exception;

import com.binah.ace.shared.exception.EntityNotFoundException;
import com.binah.ace.shared.valueobject.CPF;
import com.binah.ace.teacher.domain.entity.Teacher;

import java.util.UUID;

public class TeacherNotFoundException extends EntityNotFoundException{

    public TeacherNotFoundException(UUID id) {
        super("Teacher not found with Id: " + id);
    }

    public TeacherNotFoundException(CPF cpf) {
        super("Teacher not found with CPF: " + cpf);
    }
}
