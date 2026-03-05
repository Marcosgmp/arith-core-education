package com.binah.ace.teacher.domain.entity;

import com.binah.ace.teacher.domain.enums.Degree;
import java.util.UUID;

public class Teacher {
    private UUID id;
    private String name;
    private String email;
    private Degree degree;

    public Teacher(UUID id, String name, String email, Degree degree) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.degree = degree;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }
}
