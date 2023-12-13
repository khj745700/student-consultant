package com.consultant.application.entity.student;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "student")
@NoArgsConstructor
@SuperBuilder
@Getter
public class Student {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus;

    public boolean isActive() {
        return studentStatus.equals(StudentStatus.ACTIVE);
    }

}
