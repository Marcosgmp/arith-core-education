package com.binah.ace.teacher.infrastructure.persistence;

import com.binah.ace.teacher.domain.enums.Degree;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "teachers")

public class TeacherJpaEntity {

    @Id
    private UUID id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Degree degree;

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
