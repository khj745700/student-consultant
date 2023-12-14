package com.consultant.application.entity.consulting;

import com.consultant.application.entity.common.BaseEntity;
import com.consultant.application.entity.staff.Staff;
import com.consultant.application.entity.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "consulting")
@NoArgsConstructor
@SuperBuilder
@Getter
public class Consulting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column
    private String feedback;

    @Column(name = "is_reading", nullable = false)
    private boolean isReading; //기본값 false로 하기위해 Wrapper를 사용하지 않습니다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Staff manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id", nullable = false)
    private Staff consultant;


    public void assignManager(Staff manager) {
        this.manager = manager;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
